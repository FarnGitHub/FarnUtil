package farn.farn_util.impl.dungeon;

import farn.farn_util.api.dungeon.DungeonLoot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public interface DungeonImpl {

    void addMob(String mob, int amount);

    void removeMob(String mob);

    String getRandomMob(Random random);

    void addLoot(DungeonLoot dungeonloot, int amount);

    void addGuaranteedLoot(DungeonLoot loot);

    int getGuaranteedLootSize();

    DungeonLoot getGuaranteedLoot(int amount);

    void removeLoot(int target);

    ItemStack getRandomLoots(Random random);

}
