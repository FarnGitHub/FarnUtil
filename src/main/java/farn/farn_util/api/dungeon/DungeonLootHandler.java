package farn.farn_util.api.dungeon;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public interface DungeonLootHandler {

    /**
     * Allowed you to add custom loot or override it
     * @param previousItem the item that has been chosen previously, can be null.
     * @param random the random instance.
     * @param world the current world.
     */
    ItemStack getRandomLoot(@Nullable ItemStack previousItem, Random random, World world);
}
