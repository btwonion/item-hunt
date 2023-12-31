package dev.nyon.ithu.challenge.client

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.player.LocalPlayer

class ClientPacketHandler : ClientPlayNetworking.PlayPacketHandler<ItemHuntUpdatePacket> {
    override fun receive(packet: ItemHuntUpdatePacket?, player: LocalPlayer?, responseSender: PacketSender?) {
        ItemHuntOverlay.huntProgress = packet
    }
}