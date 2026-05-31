package farn.farn_util.impl.dungeon;

import farn.farn_util.api.dungeon.DungeonLoot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public interface DungeonImpl {

    void addMob(String mob, int amount);

    String getRandomMob(Random random);

    void addLoot(DungeonLoot dungeonloot);

    ItemStack getRandomLoots(Random random);

}
