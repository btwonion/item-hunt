package dev.nyon.ithu.challenge.client

import net.fabricmc.fabric.api.networking.v1.FabricPacket
import net.fabricmc.fabric.api.networking.v1.PacketType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

val packetIdentifier = ResourceLocation("ithu", "item_hunt_update")

data class ItemHuntUpdatePacket(val nextItem: Item?, val completed: Int, val left: Int) : FabricPacket {
    companion object {
        val packetType: PacketType<ItemHuntUpdatePacket> = PacketType.create(packetIdentifier) {
            val compound = it.readNbt()!!
            val nextItemString = compound.getString("next_item")
            val nextItem =
                if (nextItemString == "null") null else BuiltInRegistries.ITEM.get(ResourceLocation(nextItemString))
            val completed = compound.getInt("completed")
            val left = compound.getInt("left")

            return@create ItemHuntUpdatePacket(nextItem, completed, left)
        }
    }

    override fun write(buf: FriendlyByteBuf?) {
        val compound = CompoundTag()
        compound.putString(
            "next_item",
            if (nextItem == null) "null" else BuiltInRegistries.ITEM.getKey(nextItem).toString()
        )
        compound.putInt("completed", completed)
        compound.putInt("left", left)
        buf?.writeNbt(compound)
    }

    override fun getType(): PacketType<*> {
        return packetType
    }
}