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
    removeIf { val key = BuiltInRegistries.ITEM.getKey(it); key.toString().endsWith("spawn_egg") }
    return this
}