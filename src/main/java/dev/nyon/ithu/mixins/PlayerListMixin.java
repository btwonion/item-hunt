package dev.nyon.ithu.mixins;

import dev.nyon.ithu.config.ItemHuntDataKt;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(
        method = "placeNewPlayer",
        at = @At("TAIL")
    )
    public void initPlayerJoin(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        ItemHuntDataKt.getCurrentItemHuntData().initPlayerData(player.getUUID());
    }
}
