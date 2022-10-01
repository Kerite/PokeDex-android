package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.kerite.pokedex.SettingsConstants.SETTINGS_GAME_DEFAULT
import com.kerite.pokedex.SettingsConstants.SETTINGS_GAME_KEY
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.model.PokemonMoveRow
import com.kerite.pokedex.model.enums.EnumGameList
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.settingsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 宝可梦升级，技能机或者繁殖学会的技能列表
 */
class MoveLearnListViewModel(
    application: Application,
    private val state: SavedStateHandle
) : AndroidViewModel(application) {
    private val mDataBase = PokemonDatabase.getInstance(application)
    private val mMoveLearnDao = mDataBase.moveListDao()

    private val mGenerationPreference = application.settingsDataStore.data
        .map { preferences ->
            preferences[SETTINGS_GAME_KEY] ?: SETTINGS_GAME_DEFAULT
        }
    private val mGenerationQuery: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val mPokemonDexNumber: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val mPokemonFormName: MutableStateFlow<String?> = MutableStateFlow(null)
    private val mMovePattern: MutableStateFlow<MovePattern?> = MutableStateFlow(null)
    val moveLearnList = combine(
        mGenerationQuery,
        mGenerationPreference,
        mPokemonDexNumber,
        mMovePattern,
        mPokemonFormName,
    ) { _, generationPreference, dexNumber, pattern, formName ->
        if (dexNumber == null) {
            return@combine emptyList<PokemonMoveRow>()
        } else {
            val moveLearnList =
                withContext(Dispatchers.IO) { mMoveLearnDao.filterMoveLearn(dexNumber) }
            Timber.tag("DatabaseResult").d(moveLearnList.joinToString("\n"))
            val result = withContext(Dispatchers.Default) {
                moveLearnList.mapNotNull {
                    PokemonMoveRow.fromMoveLearnView(it, EnumGameList.fromInt(generationPreference))
                }.sortedWith { o1, o2 ->
                    if (o1.value == "—" && o2.value != "—") {
                        -1
                    } else if (o2.value == "—" && o1.value != "—") {
                        1
                    } else if (o2.value == "—" && o1.value == "—") {
                        o1.id - o2.id
                    } else {
                        o1.value.compareTo(o2.value)
                    }
                }
            }
            return@combine result
        }
    }

    fun setDexNumber(dexNumber: Int) {
        mPokemonDexNumber.value = dexNumber
    }

    fun setFormName(formName: String?) {
        mPokemonFormName.value = formName
    }
}