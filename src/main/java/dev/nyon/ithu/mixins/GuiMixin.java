package dev.nyon.ithu.mixins;

import dev.nyon.ithu.challenge.client.ItemHuntOverlay;
import dev.nyon.ithu.config.ItemHuntClientConfigKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
        method = "render",
        at = @At("TAIL")
    )
    public void renderOverlay(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        if (!minecraft.options.renderDebug && !minecraft.options.hideGui && ItemHuntClientConfigKt.getClientConfig().getOverlayDisplayed())
            ItemHuntOverlay.INSTANCE.render(guiGraphics);
    }
}