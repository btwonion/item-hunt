package dev.nyon.ithu.challenge.client

import dev.nyon.ithu.config.clientConfig
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

object ItemHuntOverlay : HudElement {
    val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath("ithu", "item_hunt_overlay")

    val minecraft: Minecraft = Minecraft.getInstance()
    var huntProgress: ItemHuntUpdatePacket? = null

    override fun render(
        context: GuiGraphics, tickCounter: DeltaTracker
    ) {
        val nextItem = huntProgress?.nextItem ?: return

        if (!clientConfig.hideItemIcon) {
            val matrix = context.pose().pushMatrix()
            context.pose().translate(
                if (clientConfig.center) ((context.guiWidth() / 2) - 70).toFloat() else (clientConfig.customX - 70).toFloat(),
                clientConfig.y.toFloat(),
                matrix
            )
            context.pose().scale(2F, 2F, matrix)
            context.renderItem(nextItem.defaultInstance, 0, 0)
            context.pose().popMatrix()
        }

        if (!clientConfig.hideItemName) context.drawString(
            minecraft.font,
            nextItem.name,
            if (clientConfig.center) (context.guiWidth() / 2) - 25 else clientConfig.customX + 50,
            clientConfig.y + 5,
            0xFFFFFFFF.toInt()
        )

        if (!clientConfig.hideProgress) context.drawString(
            minecraft.font,
            Component.literal("${huntProgress?.completed} / ${huntProgress?.left}"),
            if (clientConfig.center) (context.guiWidth() / 2) - 25 else clientConfig.customX + 50,
            clientConfig.y + 20,
            0xFFFFFFFF.toInt()
        )
    }
}