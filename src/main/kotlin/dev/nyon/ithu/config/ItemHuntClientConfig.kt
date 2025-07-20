package dev.nyon.ithu.config

import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText

data class ItemHuntClientConfig(
    var overlayDisplayed: Boolean = true,
    var hideProgress: Boolean = false,
    var hideItemIcon: Boolean = false,
    var hideItemName: Boolean = false,
    var center: Boolean = true,
    var customX: Int = 10,
    var y: Int = 5
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
    } catch (_: Exception) {
        saveClientConfig()
        clientConfig
    }
}