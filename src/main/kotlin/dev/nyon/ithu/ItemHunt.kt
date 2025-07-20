package dev.nyon.ithu

import dev.nyon.ithu.challenge.client.ItemHuntOverlay
import dev.nyon.ithu.challenge.client.ItemHuntUpdatePacket
import dev.nyon.ithu.challenge.currentItemHuntData
import dev.nyon.ithu.config.loadClientConfig
import dev.nyon.ithu.config.loadConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

@Suppress("unused")
object ItemHunt : ClientModInitializer, DedicatedServerModInitializer {
    override fun onInitializeClient() {
        loadConfig()
        loadClientConfig()

        PayloadTypeRegistry.playS2C().register(ItemHuntUpdatePacket.packetType, ItemHuntUpdatePacket.codec)

        ClientPlayConnectionEvents.INIT.register { _, _ ->
            ClientPlayNetworking.registerGlobalReceiver(ItemHuntUpdatePacket.packetType) { packet, _ ->
                ItemHuntOverlay.huntProgress = packet
            }
        }

        ServerPlayConnectionEvents.INIT.register { handler, _ ->
            val uuid = handler.player.uuid
            currentItemHuntData?.initPlayerData(uuid)

            val playerData = currentItemHuntData?.playerData[uuid] ?: return@register
            ServerPlayNetworking.send(handler.player, ItemHuntUpdatePacket(playerData.nextItems.firstOrNull(), playerData.foundItems.size + 1, playerData.nextItems.size + 1))
        }

        HudElementRegistry.addLast(ItemHuntOverlay.id, ItemHuntOverlay)
    }

    override fun onInitializeServer() {
        loadConfig()
    }
}