package dev.nyon.ithu.mixins;

import dev.nyon.ithu.config.ItemHuntDataKt;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Shadow
    @Final
    private ServerLevelData serverLevelData;

    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    public void initWorld(
        MinecraftServer minecraftServer,
        Executor executor,
        LevelStorageSource.LevelStorageAccess levelStorageAccess,
        ServerLevelData levelData,
        ResourceKey resourceKey,
        LevelStem levelStem,
        ChunkProgressListener chunkProgressListener,
        boolean bl,
        long l,
        List list,
        boolean bl2,
        RandomSequences randomSequences,
        CallbackInfo ci
    ) {
        ItemHuntDataKt.loadLevelData(levelData.getLevelName());
    }

    @Inject(
        method = "saveLevelData",
        at = @At("TAIL")
    )
    public void saveData(CallbackInfo ci) {
        ItemHuntDataKt.saveLevelData(serverLevelData.getLevelName());
    }
}
