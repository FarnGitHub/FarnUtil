package farn.farn_util.impl.dungeon;

import farn.farn_util.api.dungeon.DungeonLoot;
import farn.farn_util.api.dungeon.DungeonMonster;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Random;

public class NormalDungeonImpl implements DungeonImpl{
    private final ObjectArrayList<DungeonMonster> mobs = new ObjectArrayList<>();
    private final ObjectArrayList<DungeonLoot> loots = new ObjectArrayList<>();
    private static int TOTAL_MOB_WEIGHT = 0;
    private static int TOTAL_LOOT_WEIGHT = 0;

    public void addMob(String s, int i) {
        mobs.add(new DungeonMonster(s, i));
        TOTAL_MOB_WEIGHT = getTotalWeight(mobs);
    }

    public String getRandomMob(Random random) {
        if (TOTAL_MOB_WEIGHT > 0) {
            int weight = random.nextInt(TOTAL_MOB_WEIGHT);

            for (DungeonMonster monster : mobs) {
                weight -= monster.weight;
                if (weight < 0)
                    return monster.entityId;
            }
        }
        return null;
    }

    public void addLoot(DungeonLoot dungeonloot) {
        loots.add(dungeonloot);
        TOTAL_LOOT_WEIGHT = getTotalWeight(loots);
    }

    public ItemStack getRandomLoots(Random random) {
        if (TOTAL_LOOT_WEIGHT > 0) {
            int weight = random.nextInt(TOTAL_LOOT_WEIGHT);

            for (DungeonLoot loot : loots) {
                weight -= loot.weight;
                if (weight < 0) {
                    ItemStack lootItem = loot.loot.copy();
                    lootItem.count = loot.min + random.nextInt(loot.max - loot.min + 1);
                    return lootItem;
                }
            }
        }
        return null;
    }

    public static int getTotalWeight(Collection<? extends DungeonWeight> list) {
        int total = 0;
        for(DungeonWeight loot : list) {
            total += loot.weight;
        }
        return total;
    }
}
