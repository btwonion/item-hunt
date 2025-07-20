package dev.nyon.ithu.challenge.client

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

val packetIdentifier: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ithu", "item_hunt_update")

data class ItemHuntUpdatePacket(val nextItem: Item?, val completed: Int, val left: Int) : CustomPacketPayload {
    companion object {
        val packetType: CustomPacketPayload.Type<ItemHuntUpdatePacket> = CustomPacketPayload.Type(packetIdentifier)

        @Suppress("unused")
        val codec = object : StreamCodec<FriendlyByteBuf, ItemHuntUpdatePacket> {
            override fun decode(buf: FriendlyByteBuf): ItemHuntUpdatePacket {
                val nextItemString = buf.readUtf()
                val nextItem = if (nextItemString.isEmpty()) null else BuiltInRegistries.ITEM.get(
                    ResourceLocation.parse(nextItemString)
                ).get().value()
                val completed = buf.readInt()
                val left = buf.readInt()
                return ItemHuntUpdatePacket(nextItem, completed, left)
            }

            override fun encode(buf: FriendlyByteBuf, packet: ItemHuntUpdatePacket) {
                buf.writeUtf(
                    if (packet.nextItem == null) "" else BuiltInRegistries.ITEM.getKey(packet.nextItem).toString()
                )
                buf.writeInt(packet.completed)
                buf.writeInt(packet.left)
            }
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?>? {
        return packetType
    }
}