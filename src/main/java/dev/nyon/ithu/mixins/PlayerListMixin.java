package dev.nyon.ithu.mixins;

import dev.nyon.ithu.challenge.ItemHuntData;
import dev.nyon.ithu.challenge.ItemHuntDataHolder;
import dev.nyon.ithu.challenge.ItemHuntDataKt;
import dev.nyon.ithu.challenge.client.ItemHuntUpdatePacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
        at = @At("HEAD")
    )
    public void initPlayerJoin(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        ItemHuntDataKt.getCurrentItemHuntData().initPlayerData(player.getUUID());
    }

    @Inject(
        method = "placeNewPlayer",
        at = @At("TAIL")
    )
    public void sendProgressPacket(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        ItemHuntDataHolder data = ItemHuntDataKt.getCurrentItemHuntData();
        ItemHuntData playerData = data.getPlayerData().get(player.getUUID());
        ServerPlayNetworking.send(player, new ItemHuntUpdatePacket(playerData.getNextItems().stream().findFirst().orElseGet(null), playerData.getFoundItems().size() + 1, playerData.getNextItems().size() + 1));
    }
}
