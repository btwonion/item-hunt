package dev.nyon.ithu.config

import dev.nyon.ithu.utils.ItemSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.world.item.Item
import kotlin.io.path.*

@Serializable
data class ItemHuntConfig(
    var defaultExcludeConfig: ExcludeConfig = ExcludeConfig()
)

@Serializable
data class ExcludeConfig(
    var excludeCreatives: Boolean = true,
    var custom: MutableList<@Serializable(with = ItemSerializer::class) Item> = mutableListOf()
)

val json: Json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}
val configPath = FabricLoader.getInstance().configDir.resolve("item-hunt/").also { it.createDirectories() }
var generalConfig = ItemHuntConfig()
val generalConfigFile = configPath.resolve("config.json").also { if (it.notExists()) it.createFile() }

fun saveConfig() {
    generalConfigFile.writeText(json.encodeToString(generalConfig))
}

fun loadConfig() {
    val text = generalConfigFile.readText()

    generalConfig = try {
        json.decodeFromString<ItemHuntConfig>(text)
    } catch (e: Exception) {
        saveConfig()
        generalConfig
    }
}