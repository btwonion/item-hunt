package dev.nyon.ithu.mixins;

import dev.nyon.ithu.challenge.ItemHuntDataKt;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryChangeTrigger.class)
public class InventoryChangeTriggerMixin {

    @Inject(
        method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V",
        at = @At("HEAD")
    )
    public void triggerInventoryChange(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo ci) {
        Item item = stack.getItem();

        Item nextItem = ItemHuntDataKt.getCurrentItemHuntData().getPlayerData().get(player.getUUID()).getNextItems().stream().findFirst().orElse(Items.AIR);

        if (nextItem.equals(item)) ItemHuntDataKt.getCurrentItemHuntData().awardItemToPlayer(player, item);
    }
}
