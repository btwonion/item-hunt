package dev.nyon.ithu.challenge.client

import dev.nyon.ithu.config.clientConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

object ItemHuntOverlay {
    val minecraft: Minecraft = Minecraft.getInstance()
    var huntProgress: ItemHuntUpdatePacket? = null

    fun render(context: GuiGraphics) {
        val nextItem = huntProgress?.nextItem ?: return

        if (!clientConfig.hideItemIcon) {
            context.pose().pushPose()
            context.pose().translate(
                if (clientConfig.center) ((context.guiWidth() / 2) - 70).toFloat() else (clientConfig.customX - 70).toFloat(),
                clientConfig.y.toFloat(),
                0F
            )
            context.pose().scale(2F, 2F, 0F)
            context.renderItem(nextItem.defaultInstance, 0, 0)
            context.pose().popPose()
        }

        if (!clientConfig.hideItemName) context.drawString(
            minecraft.font,
            nextItem.description,
            if (clientConfig.center) (context.guiWidth() / 2) - 25 else clientConfig.customX + 50,
            clientConfig.y + 5,
            0xFFFFFF
        )

        if (!clientConfig.hideProgress) context.drawString(
            minecraft.font,
            Component.literal("${huntProgress?.completed} / ${huntProgress?.left}"),
            if (clientConfig.center) (context.guiWidth() / 2) - 25 else clientConfig.customX + 50,
            clientConfig.y + 20,
            0xFFFFFF
        )
    }
}