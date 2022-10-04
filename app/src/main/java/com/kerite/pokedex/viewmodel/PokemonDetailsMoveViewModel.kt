package com.kerite.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.kerite.pokedex.SettingsConstants
import com.kerite.pokedex.database.PokemonDatabase
import com.kerite.pokedex.model.PokemonMoveRow
import com.kerite.pokedex.model.enums.EnumGame
import com.kerite.pokedex.model.enums.MovePattern
import com.kerite.pokedex.settingsDataStore
import com.kerite.pokedex.ui.fragment.pokemondetails.PokeDetailsMoveFragment
import com.kerite.pokedex.util.extension.isNumeric
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonDetailsMoveViewModel(
    application: Application,
    private val savedState: SavedStateHandle
) : AndroidViewModel(application) {
    private val mMoveLearnDao = PokemonDatabase.getInstance(application)
        .moveListDao()

    private val mMovePatternFlow =
        savedState.getStateFlow(PokeDetailsMoveFragment.STATE_MOVE_PATTERN, MovePattern.LEVEL)
    private val mDexNumber =
        savedState.getStateFlow(PokeDetailsMoveFragment.STATE_DEX_NUMBER, -1)
    private val mFormName =
        savedState.getStateFlow(PokeDetailsMoveFragment.STATE_FORM_NAME, null as String?)
    private val mGameVersionPreference = application.settingsDataStore.data
        .map { preferences ->
            preferences[SettingsConstants.SETTINGS_GAME_KEY]
                ?: SettingsConstants.SETTINGS_GAME_DEFAULT
        }

    @OptIn(FlowPreview::class)
    val moveList: Flow<List<PokemonMoveRow>> = combine(
        mMovePatternFlow,
        mDexNumber,
        mFormName,
        mGameVersionPreference
    ) { pattern, dexNumber, formName, gameVersion ->
        val version = EnumGame.valueOf(gameVersion)
        if (dexNumber == -1) return@combine emptyList()
        when (pattern) {
            MovePattern.TEACH -> getMoveTeachList(dexNumber, formName, version)
            else -> getMoveLearnList(dexNumber, formName, version, pattern)
        }
    }.distinctUntilChanged().debounce(200)

    fun setMovePattern(pattern: MovePattern) {
        savedState[PokeDetailsMoveFragment.STATE_MOVE_PATTERN] = pattern
    }

    fun setDexNumber(dexNumber: Int) {
        savedState[PokeDetailsMoveFragment.STATE_DEX_NUMBER] = dexNumber
    }

    fun setFormName(formName: String?) {
        savedState[PokeDetailsMoveFragment.STATE_FORM_NAME] = formName
    }

    private suspend fun getMoveLearnList(
        dexNumber: Int,
        formName: String?,
        gameVersion: EnumGame,
        movePattern: MovePattern
    ): List<PokemonMoveRow> = withContext(Dispatchers.IO) {
//        Timber.tag("MoveFilter").d("GameVersion ${gameVersion.name}")
        val moveLearnList =
            if (formName == null) {
//                Timber.tag("MoveFilter").d("FormName is NULL")
                mMoveLearnDao.filterMoveLearn(dexNumber, movePattern)
            } else {
//                Timber.tag("MoveFilter").d("FormName $formName")
                mMoveLearnDao.filterMoveLearn(dexNumber, formName, movePattern)
            }
        withContext(Dispatchers.Default) {
            moveLearnList.mapNotNull {
                PokemonMoveRow.fromMoveLearnView(it, gameVersion)
            }.sortedWith { o1, o2 ->
                if (o1.value == "进化" && o2.value != "进化") {
                    -1
                } else if (o1.value != "进化" && o2.value == "进化") {
                    1
                } else if (o1.value == "进化" && o2.value == "进化") {
                    o1.id - o2.id
                } else if (o1.value == "—" && o2.value != "—") {
                    -1
                } else if (o2.value == "—" && o1.value != "—") {
                    1
                } else if (o2.value == "—" && o1.value == "—") {
                    o1.id - o2.id
                } else if (o2.value.isNumeric() && o1.value.isNumeric()) {
                    o1.value.toInt() - o2.value.toInt()
                } else {
                    o1.value.compareTo(o2.value)
                }
            }
        }
    }

    private suspend fun getMoveTeachList(
        dexNumber: Int,
        formName: String?,
        gameVersion: EnumGame
    ): List<PokemonMoveRow> = withContext(Dispatchers.IO) {
        val moveList = if (formName == null) {
            mMoveLearnDao.filterMoveTeach(dexNumber)
        } else {
            mMoveLearnDao.filterMoveTeach(dexNumber, formName)
        }
        withContext(Dispatchers.Default) {
            moveList.mapNotNull {
                PokemonMoveRow.fromMoveTeachView(it, gameVersion)
            }.sortedBy { it.id }
        }
    }
}