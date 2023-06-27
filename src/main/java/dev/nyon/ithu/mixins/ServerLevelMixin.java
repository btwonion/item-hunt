package dev.nyon.ithu.mixins;

import dev.nyon.ithu.challenge.ItemHuntDataKt;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Shadow
    public abstract @NotNull MinecraftServer getServer();

    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    public void initWorld(
        MinecraftServer minecraftServer,
        Executor executor,
        LevelStorageSource.LevelStorageAccess levelStorageAccess,
        ServerLevelData levelData,
        ResourceKey<Level> resourceKey,
        LevelStem levelStem,
        ChunkProgressListener chunkProgressListener,
        boolean bl,
        long l,
        List<CustomSpawner> list,
        boolean bl2,
        RandomSequences randomSequences,
        CallbackInfo ci
    ) {
        ItemHuntDataKt.loadLevelData(levelStorageAccess.getLevelId());
    }

    @Inject(
        method = "saveLevelData",
        at = @At("TAIL")
    )
    public void saveData(CallbackInfo ci) {
        ItemHuntDataKt.saveLevelData(((MinecraftServerAccessor) getServer()).getStorageSource().getLevelId());
    }
}
