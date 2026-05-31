package farn.farn_util.impl.dungeon;

import farn.farn_util.api.dungeon.DungeonLoot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class NormalDungeonImpl implements DungeonImpl{
    private final ArrayList<String> mobs = new ArrayList<>();
    private final ArrayList<DungeonLoot> loot = new ArrayList<>();
    private final ArrayList<DungeonLoot> guaranteedLoot = new ArrayList<>();
    private static final int POSSIBLE_MOB = 4;
    private static final int POSSIBLE_LOOT = 761;

    public void addMob(String s, int i) {
        for(int j = 0; j < i; ++j) {
            mobs.add(s);
        }

    }

    public void removeMob(String s) {
        for(int i = 0; i < mobs.size(); ++i) {
            if (mobs.get(i).equals(s)) {
                mobs.remove(i);
                --i;
            }
        }

    }

    public String getRandomMob(Random random) {
        if(mobs.isEmpty()) return "";
        int index = random.nextInt(POSSIBLE_MOB + mobs.size());
        if(index < POSSIBLE_MOB) return "";
        return mobs.get(index - POSSIBLE_MOB);
    }

    public void addLoot(DungeonLoot dungeonloot, int amount) {
        for(int j = 0; j < amount; ++j) {
            loot.add(dungeonloot);
        }

    }

    public void addGuaranteedLoot(DungeonLoot dungeonloot) {
        guaranteedLoot.add(dungeonloot);
    }


    public int getGuaranteedLootSize() {
        return guaranteedLoot.size();
    }


    public DungeonLoot getGuaranteedLoot(int i) {
        return guaranteedLoot.get(i);
    }

    public void removeLoot(int i) {
        for(int j = 0; j < loot.size(); ++j) {
            if (loot.get(j).loot.itemId == i) {
                loot.remove(j);
                --j;
            }
        }

        for(int k = 0; k < guaranteedLoot.size(); ++k) {
            if (guaranteedLoot.get(k).loot.itemId == i) {
                guaranteedLoot.remove(k);
                --k;
            }
        }

    }

    public ItemStack getRandomLoots(Random random) {
        if(loot.isEmpty()) return null;
        int index = random.nextInt(POSSIBLE_LOOT + loot.size());
        if(index < POSSIBLE_LOOT) return null;
        return loot.get(index - POSSIBLE_LOOT).getStack(random);
    }
}
