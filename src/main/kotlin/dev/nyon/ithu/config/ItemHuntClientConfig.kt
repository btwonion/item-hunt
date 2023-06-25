package dev.nyon.ithu.config

import kotlinx.serialization.encodeToString
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText

data class ItemHuntClientConfig(
    val overlayDisplayed: Boolean = true
)

val clientConfigFile: Path = configPath.resolve("client-config.json").also { if (it.notExists()) it.createFile() }
var clientConfig: ItemHuntClientConfig = ItemHuntClientConfig()

fun saveClientConfig() {
    clientConfigFile.writeText(json.encodeToString(generalConfig))
}

fun loadClientConfig() {
    val text = clientConfigFile.readText()

    clientConfig = try {
        json.decodeFromString<ItemHuntClientConfig>(text)
    } catch (e: Exception) {
        saveClientConfig()
        clientConfig
    }
}