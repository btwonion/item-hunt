package dev.nyon.ithu

import dev.nyon.ithu.config.loadClientConfig
import dev.nyon.ithu.config.loadConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer

@Suppress("unused")
object ItemHunt : ClientModInitializer, ModInitializer {
    override fun onInitializeClient() {
        loadClientConfig()
    }

    override fun onInitialize() {
        loadConfig()
    }
}