package farn.farn_util.api.dungeon;

import net.minecraft.world.World;

import java.util.Random;

public interface DungeonMobHandler {

    /**
     * Allowed you to add custom loot or override it
     * @param previousMob mob that has been chosen previously.
     * @param random the random instance.
     * @param world the current world.
     */
    String getRandomMob(String previousMob, Random random, World world);
}
