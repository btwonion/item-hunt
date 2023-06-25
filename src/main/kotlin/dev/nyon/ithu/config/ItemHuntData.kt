package dev.nyon.ithu.config

import dev.nyon.ithu.utils.ItemSerializer
import dev.nyon.ithu.utils.UUIDSerializer
import dev.nyon.ithu.utils.removeCreativeItems
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import java.util.*
import kotlin.io.path.*


var currentItemHuntData: ItemHuntDataHolder? = null

@Serializable
data class ItemHuntDataHolder(
    var useDefaultExcludeConfig: Boolean = true,
    var excludeConfig: ExcludeConfig = ExcludeConfig(),
    var playerData: MutableMap<@Serializable(with = UUIDSerializer::class) UUID, ItemHuntData> = mutableMapOf()
) {
    fun initPlayerData(uuid: UUID): ItemHuntData {
        println("player")
        var data = playerData[uuid]
        if (data == null) {
            val newData = ItemHuntData()
            playerData[uuid] = newData
            data = newData
            data.nextItems = BuiltInRegistries.ITEM.toMutableList().removeCreativeItems().shuffled().toMutableList()
        }

        return data
    }

    fun awardItemToPlayer(uuid: UUID, item: Item) {
        val data = playerData[uuid] ?: return
        data.foundItems += item
        data.nextItems -= item
    }
}

@Serializable
data class ItemHuntData(
    val enabled: Boolean = true,
    var foundItems: MutableList<@Serializable(with = ItemSerializer::class) Item> = mutableListOf(),
    var nextItems: MutableList<@Serializable(with = ItemSerializer::class) Item> = mutableListOf()
)

fun loadLevelData(name: String) {
    println("world")
    val file = configPath.resolve("$name.data.json").also { if (it.notExists()) it.createFile() }
    val fileText = file.readText()
    if (fileText.isBlank()) {
        currentItemHuntData = ItemHuntDataHolder()
        return
    }

    currentItemHuntData = try {
        json.decodeFromString<ItemHuntDataHolder>(fileText)
    } catch (e: Exception) {
        ItemHuntDataHolder()
    }
}

fun saveLevelData(name: String) {
    val file = configPath.resolve("$name.data.json").also { if (it.notExists()) it.createFile() }

    file.writeText(json.encodeToString(currentItemHuntData))
}