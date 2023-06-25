package dev.nyon.ithu.mixins;

import dev.nyon.ithu.config.ItemHuntDataKt;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    private final ServerPlayer player = (ServerPlayer) (Object) this;

    @Shadow
    public abstract ServerLevel serverLevel();

    @Inject(
        method = "triggerRecipeCrafted",
        at = @At("TAIL")
    )
    public void awardItemOnCraft(Recipe<?> recipe, List<ItemStack> list, CallbackInfo ci) {
        Item item = recipe.getResultItem(serverLevel().registryAccess()).getItem();
        UUID uuid = player.getUUID();

        if (ItemHuntDataKt.getCurrentItemHuntData().getPlayerData().get(uuid).getNextItems().stream().findFirst().equals(item))
            ItemHuntDataKt.getCurrentItemHuntData().awardItemToPlayer(uuid, item);
    }

    @Inject(
        method = "onItemPickup",
        at = @At("TAIL")
    )
    public void awardItemOnPickup(ItemEntity itemEntity, CallbackInfo ci) {
        Item item = itemEntity.getItem().getItem();
        UUID uuid = player.getUUID();

        if (ItemHuntDataKt.getCurrentItemHuntData().getPlayerData().get(uuid).getNextItems().stream().findFirst().equals(item))
            ItemHuntDataKt.getCurrentItemHuntData().awardItemToPlayer(uuid, item);
    }
}
