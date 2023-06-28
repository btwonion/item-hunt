package dev.nyon.ithu.config.client

import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.nyon.ithu.config.clientConfig
import dev.nyon.ithu.config.saveClientConfig
import dev.nyon.ithu.config.saveConfig
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

fun generateConfigScreen(parent: Screen? = null): Screen {
    val configScreenBuilder = YetAnotherConfigLib.createBuilder()
    configScreenBuilder.title(Component.literal("item-hunt"))
    configScreenBuilder.appendClientSettingCategory()
    configScreenBuilder.save { saveConfig(); saveClientConfig() }
    val configScreen = configScreenBuilder.build()

    return configScreen.generateScreen(parent)
}

fun YetAnotherConfigLib.Builder.appendClientSettingCategory() {
    this.category(
        ConfigCategory.createBuilder()
            .name(Component.literal("Client Settings"))
            .option(
                Option.createBuilder<Boolean>()
                    .name(Component.literal("overlay displayed"))
                    .description(OptionDescription.of(Component.literal("Should the item hunt overlay be displayed?")))
                    .binding(
                        clientConfig.overlayDisplayed,
                        { clientConfig.overlayDisplayed },
                        { clientConfig.overlayDisplayed = it })
                    .controller {
                        TickBoxControllerBuilder.create(it)
                    }.build()
            )
            .option(
                Option.createBuilder<Boolean>()
                    .name(Component.literal("hide progress"))
                    .description(OptionDescription.of(Component.literal("Should the progress be displayed?")))
                    .binding(
                        clientConfig.hideProgress,
                        { clientConfig.hideProgress },
                        { clientConfig.hideProgress = it })
                    .controller {
                        TickBoxControllerBuilder.create(it)
                    }.build()
            )
            .option(
                Option.createBuilder<Boolean>()
                    .name(Component.literal("hide item icon"))
                    .description(
                        OptionDescription.of(Component.literal("Should the item's icon be displayed?"))
                    )
                    .binding(
                        clientConfig.hideItemIcon,
                        { clientConfig.hideItemIcon },
                        { clientConfig.hideItemIcon = it })
                    .controller {
                        TickBoxControllerBuilder.create(it)
                    }.build()
            )
            .option(
                Option.createBuilder<Boolean>()
                    .name(Component.literal("hide item name"))
                    .description(OptionDescription.of(Component.literal("Should the item's name be displayed?")))
                    .binding(
                        clientConfig.hideItemName,
                        { clientConfig.hideItemName },
                        { clientConfig.hideItemName = it })
                    .controller {
                        TickBoxControllerBuilder.create(it)
                    }.build()
            )
            .option(
                Option.createBuilder<Boolean>()
                    .name(Component.literal("center components"))
                    .description(OptionDescription.of(Component.literal("Should the item-hunt overlay components be x-centered?")))
                    .binding(clientConfig.center, { clientConfig.center }, { clientConfig.center = it })
                    .controller {
                        TickBoxControllerBuilder.create(it)
                    }.build()
            )
            .option(
                Option.createBuilder<Int>()
                    .name(Component.literal("custom x"))
                    .description(
                        OptionDescription.of(
                            Component.literal("The x coordinate where the overlay components should be displayed."),
                            Component.literal("Requires 'center components' to be disabled!")
                        )
                    )
                    .binding(clientConfig.customX, { clientConfig.customX }, { clientConfig.customX = it })
                    .controller {
                        IntegerFieldControllerBuilder.create(it)
                    }.build()
            )
            .option(
                Option.createBuilder<Int>()
                    .name(Component.literal("y"))
                    .description(OptionDescription.of(Component.literal("The y coordinate where the overlay components should be displayed.")))
                    .binding(clientConfig.y, { clientConfig.y }, { clientConfig.y = it })
                    .controller {
                        IntegerFieldControllerBuilder.create(it)
                    }.build()
            )
            .build()
    )
}