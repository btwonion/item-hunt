package dev.nyon.ithu.utils

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items

fun MutableList<Item>.removeCreativeItems(): MutableList<Item> {
    remove(Items.DEBUG_STICK)
    remove(Items.COMMAND_BLOCK)
    remove(Items.COMMAND_BLOCK_MINECART)
    remove(Items.CHAIN_COMMAND_BLOCK)
    remove(Items.REPEATING_COMMAND_BLOCK)
    remove(Items.SPAWNER)
    remove(Items.AIR)
    remove(Items.BEDROCK)
    remove(Items.LIGHT)
    remove(Items.BARRIER)
    remove(Items.STRUCTURE_BLOCK)
    remove(Items.STRUCTURE_VOID)
    remove(Items.JIGSAW)
    remove(Items.PETRIFIED_OAK_SLAB)
    remove(Items.PLAYER_HEAD)
    remove(Items.KNOWLEDGE_BOOK)
    remove(Items.BUDDING_AMETHYST)
    remove(Items.CHORUS_PLANT)
    remove(Items.DIRT_PATH)
    remove(Items.END_PORTAL_FRAME)
    remove(Items.FARMLAND)

    remove(Items.INFESTED_COBBLESTONE)
    remove(Items.INFESTED_CHISELED_STONE_BRICKS)
    remove(Items.INFESTED_DEEPSLATE)
    remove(Items.INFESTED_STONE)
    remove(Items.INFESTED_CRACKED_STONE_BRICKS)
    remove(Items.INFESTED_MOSSY_STONE_BRICKS)
    remove(Items.INFESTED_STONE_BRICKS)

    remove(Items.BUNDLE)

    removeIf { val key = BuiltInRegistries.ITEM.getKey(it); key.toString().endsWith("spawn_egg") }
    return this
}