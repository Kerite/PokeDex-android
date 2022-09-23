package com.kerite.pokedex.ui.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.request.Disposable
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.kerite.pokedex.database.entity.PokemonDetailsEntity
import com.kerite.pokedex.databinding.ActivityPokemonDetailsBinding
import com.kerite.pokedex.model.PokemonDetails
import com.kerite.pokedex.ui.BaseActivity
import com.kerite.pokedex.viewmodel.DetailsActivityViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PokemonDetailsActivity : BaseActivity<ActivityPokemonDetailsBinding>(
    ActivityPokemonDetailsBinding::inflate
) {
    private lateinit var viewModel: DetailsActivityViewModel
    private var imageLoadDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsActivityViewModel::class.java]
        viewModel.changePokemonDexNumber(intent.getIntExtra(INTENT_DEX_NUMBER, 0))

        binding.apply {
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            lifecycleScope.launch {
                viewModel.currentPokemonDetail.collect {
                    when (it) {
                        is PokemonDetails.Found -> {
                            updateDetails(it.details)
                        }

                        else -> {
                            initPokemonDetail()
                        }
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.pokemonDetails.collect {
                    pokemonSubnameTab.removeAllTabs()
                    for (detail in it) {
                        pokemonSubnameTab.addTab(pokemonSubnameTab.newTab().setText(detail.formName ?: detail.name))
                    }
                }
            }
            pokemonSubnameTab.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewModel.changePokemonIndex(tab.position)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
    }

    private fun initPokemonDetail() {
        binding.apply {
            collapsingToolbar.title = "Pokemon Dex"
            evHp.text = "0"
            evAttack.text = "0"
            evDefence.text = "0"
            evSpecialAttack.text = "0"
            evSpecialDefence.text = "0"
            evSpeed.text = "0"

            pokemonStrength.hp = 0
            pokemonStrength.attack = 0
            pokemonStrength.defence = 0
            pokemonStrength.specialAttack = 0
            pokemonStrength.specialDefence = 0
            pokemonStrength.speed = 0
        }
    }

    private fun updateDetails(details: PokemonDetailsEntity) {
        Timber.tag("PokemonChanged").d(details.toString())
        binding.apply {
            collapsingToolbar.title = details.name
            pokemonDexNumber.text = "#" + details.dexNumber.toString()
            pokemonJpName.text = details.jpName
            pokemonEnName.text = details.enName
//            CoroutineScope(Dispatchers.IO).launch {
//                val imageUrl = Uri.parse(
//                    "file:///android_asset/images/" + decidePicName(details.dexNumber, details.formName, details.name)
//                )
//                Timber.d("Load Image" + imageUrl.path)
//
//            }
            val text = "file:///android_asset/images/${details.dexNumber}#${details.name}#${details.formName ?: ""}#.webp".replace("##", "#")
            Timber.tag("LoadImage").d(text)
            Glide.with(this@PokemonDetailsActivity)
                .load(Uri.parse(text))
                .into(pokemonImage)
//            if (imageLoadDisposable != null) {
//                imageLoadDisposable!!.dispose()
//            }
//            imageLoadDisposable = pokemonImage.load(Uri.parse(text))
//            Glide.with(this@PokemonDetailsActivity)
//                .load(Uri.parse(text))
//                .into(pokemonImage)
            pokemonHeight.text = details.height + " m"
            pokemonWeight.text = details.weight + " kg"

            pokemonType1.type = details.type1
            if (details.type2 != null) {
                pokemonType2.visibility = View.VISIBLE
                pokemonTypeSpace.visibility = View.VISIBLE
                pokemonType2.type = details.type2
            } else {
                pokemonType2.visibility = View.GONE
                pokemonTypeSpace.visibility = View.GONE
            }

            pokemonBodyImage.setImageResource(details.body.icon)
            pokemonBody.setText(details.body.displayedName)
            catchRate.text = details.catchRate.toString()

            //<editor-fold desc="读取努力值" defaultstate="collapsed">
            evHp.text = details.evHp.toString()
            evAttack.text = details.evAttack.toString()
            evDefence.text = details.evDefence.toString()
            evSpecialAttack.text = details.evSpecialAttack.toString()
            evSpecialDefence.text = details.evSpecialDefence.toString()
            evSpeed.text = details.evSpeed.toString()
            //</editor-fold>

            //<editor-fold desc="读取种族值" defaultstate="collapsed">
            pokemonStrength.hp = details.hp
            pokemonStrength.attack = details.attack
            pokemonStrength.defence = details.defence
            pokemonStrength.specialAttack = details.specialAttack
            pokemonStrength.specialDefence = details.specialDefence
            pokemonStrength.speed = details.speed
            //</editor-fold>
        }
    }

    /**
     * 此方法用于获取宝可梦的图像
     */
    private fun decidePicName(dexNumber: Int, subName: String? = null, pokemonName: String? = null): String {
        val assetList =
            assets.list("images")?.filter { it.contains(String.format("%03d", dexNumber)) }
        for (file in assetList!!) {
            val splitFile = file.split("#")
            Timber.d(subName + " " + splitFile.joinToString(", "))
            if (subName?.replace("的样子", "") == splitFile.last().replace(".webp", "")) {
                return file
            }
            if (subName == null && !splitFile[0].contains("_")) {
                return file
            }
            if (subName?.replace(pokemonName ?: "", "") == splitFile.last().replace("的样子", "").replace(".webp", "")) {
                return file
            }
        }
        return assetList.first()
    }

    companion object {
        const val INTENT_DEX_NUMBER = "pokemon_dex"
        private const val ASSET_POKEMON_IMAGE_DIR = "file:///android_asset/images/"
    }
}