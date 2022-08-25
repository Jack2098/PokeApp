package com.jack.pokemonapp.util

import androidx.compose.ui.graphics.Color
import com.jack.pokemonapp.ui.theme.*

sealed class PokemonBackgrounds(var name: String, var bg1: Color, var bg2: Color) {
    object Normal: PokemonBackgrounds("normal", bgNormal1, bgNormal2)
    object Fighting: PokemonBackgrounds("fighting", bgFighting1, bgFighting2)
    object Flying: PokemonBackgrounds("flying", bgFlying1, bgFlying2)
    object Poison: PokemonBackgrounds("poison", bgPoison1, bgPoison2)
    object Ground: PokemonBackgrounds("ground", bgGround1, bgGround2)
    object Rock: PokemonBackgrounds("rock", bgRock1, bgRock2)
    object Bug: PokemonBackgrounds("bug", bgBug1, bgBug2)
    object Ghost: PokemonBackgrounds("ghost", bgGhost1, bgGhost2)
    object Steel: PokemonBackgrounds("steel", bgSteel1, bgSteel2)
    object Fire: PokemonBackgrounds("fire", bgFire1, bgFire2)
    object Water: PokemonBackgrounds("water", bgWater1, bgWater2)
    object Grass: PokemonBackgrounds("grass", bgGrass1, bgGrass2)
    object Electric: PokemonBackgrounds("electric", bgElectric1, bgElectric2)
    object Psychic: PokemonBackgrounds("psychic", bgPsychic1, bgPsychic2)
    object Ice: PokemonBackgrounds("ice", bgIce1, bgIce2)
    object Dragon: PokemonBackgrounds("dragon", bgDragon1, bgDragon2)
    object Dark: PokemonBackgrounds("dark", bgDark1, bgDark2)
    object Fairy: PokemonBackgrounds("fairy", bgFairy1, bgFairy2)
}
