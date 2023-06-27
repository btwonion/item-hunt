package dev.nyon.ithu

import dev.nyon.ithu.challenge.client.ClientPacketHandler
import dev.nyon.ithu.challenge.client.ItemHuntUpdatePacket
import dev.nyon.ithu.config.loadClientConfig
import dev.nyon.ithu.config.loadConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking


@Suppress("unused")
object ItemHunt : ClientModInitializer, DedicatedServerModInitializer {
    override fun onInitializeClient() {
        loadConfig()
        loadClientConfig()

        ClientPlayNetworking.registerGlobalReceiver(ItemHuntUpdatePacket.packetType, ClientPacketHandler())
    }

    override fun onInitializeServer() {
        loadConfig()
    }
}