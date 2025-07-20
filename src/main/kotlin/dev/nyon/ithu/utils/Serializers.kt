package dev.nyon.ithu.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import java.util.UUID

object ItemSerializer : KSerializer<Item> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("item", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Item {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(decoder.decodeString())).get().value()
    }

    override fun serialize(encoder: Encoder, value: Item) {
        encoder.encodeString(BuiltInRegistries.ITEM.getKey(value).toString())
    }
}

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("uuid", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}