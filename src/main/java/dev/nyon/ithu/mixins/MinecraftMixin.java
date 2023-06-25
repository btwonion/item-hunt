package dev.nyon.ithu.mixins;

import dev.nyon.ithu.config.ItemHuntClientConfigKt;
import dev.nyon.ithu.config.ItemHuntConfigKt;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(
        method = "stop",
        at = @At("HEAD")
    )
    public void saveConfig(CallbackInfo ci) {
        ItemHuntClientConfigKt.saveClientConfig();
        ItemHuntConfigKt.saveConfig();
    }
}
