package dev.nyon.ithu.challenge

import dev.nyon.ithu.challenge.client.ItemHuntUpdatePacket
import dev.nyon.ithu.config.ExcludeConfig
import dev.nyon.ithu.config.configPath
import dev.nyon.ithu.config.json
import dev.nyon.ithu.utils.ItemSerializer
import dev.nyon.ithu.utils.UUIDSerializer
import dev.nyon.ithu.utils.removeCreativeItems
import kotlinx.serialization.Serializable
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.Item
import java.util.*
import kotlin.io.path.createFile
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText


var currentItemHuntData: ItemHuntDataHolder? = null

@Serializable
data class ItemHuntDataHolder(
    var useDefaultExcludeConfig: Boolean = true,
    var excludeConfig: ExcludeConfig = ExcludeConfig(),
    var playerData: MutableMap<@Serializable(with = UUIDSerializer::class) UUID, ItemHuntData> = mutableMapOf()
) {
    fun initPlayerData(uuid: UUID): ItemHuntData {
        var data = playerData[uuid]
        if (data == null) {
            val newData = ItemHuntData()
            playerData[uuid] = newData
            data = newData
            data.nextItems = BuiltInRegistries.ITEM.toMutableList().removeCreativeItems().shuffled().toMutableList()
        }

        return data
    }

    fun awardItemToPlayer(player: ServerPlayer, item: Item) {
        val data = playerData[player.uuid] ?: return
        data.foundItems += item
        data.nextItems -= item

        player.playSound(SoundEvents.NOTE_BLOCK_PLING.value())
        player.sendSystemMessage(
            Component.literal("You completed the item: ").withStyle(Style.EMPTY.withColor(0xBAA46D))
                .append(
                    item.name.copy().withStyle(Style.EMPTY.withColor(0xBA7C6B).withBold(true))
                )
                .append(
                    Component.literal("!").withStyle(Style.EMPTY.withColor(0xBAA46D))
                )
        )
        player.sendSystemMessage(
            Component.literal("Next up is: ").withStyle(Style.EMPTY.withColor(0xBAA46D))
                .append(
                    data.nextItems.first().name.copy().withStyle(Style.EMPTY.withColor(0xBA7C6B).withBold(true))
                )
                .append(
                    Component.literal("!").withStyle(Style.EMPTY.withColor(0xBAA46D))
                )
        )

        ServerPlayNetworking.send(
            player,
            ItemHuntUpdatePacket(data.nextItems.firstOrNull(), data.foundItems.size + 1, data.nextItems.size + 1)
        )
    }
}

@Serializable
data class ItemHuntData(
    val enabled: Boolean = true,
    var foundItems: MutableList<@Serializable(with = ItemSerializer::class) Item> = mutableListOf(),
    var nextItems: MutableList<@Serializable(with = ItemSerializer::class) Item> = mutableListOf()
)

fun loadLevelData(name: String) {
    val file = configPath.resolve("$name.data.json").also { if (it.notExists()) it.createFile() }
    val fileText = file.readText()
    if (fileText.isBlank()) {
        currentItemHuntData = ItemHuntDataHolder()
        return
    }

    currentItemHuntData = try {
        json.decodeFromString<ItemHuntDataHolder>(fileText)
    } catch (_: Exception) {
        ItemHuntDataHolder()
    }
}

fun saveLevelData(name: String) {
    val file = configPath.resolve("$name.data.json").also { if (it.notExists()) it.createFile() }

    file.writeText(json.encodeToString(currentItemHuntData))
}