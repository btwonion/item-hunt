package dev.nyon.ithu.config.client

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

@Suppress("unused")
class ModMenuImpl : ModMenuApi {

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory {
            generateConfigScreen(it)
        }
    }
}