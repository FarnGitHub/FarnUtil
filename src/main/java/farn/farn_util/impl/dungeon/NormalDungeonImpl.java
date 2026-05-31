package farn.farn_util.impl.dungeon;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.dungeon.DungeonLoot;
import farn.farn_util.api.dungeon.event.DungeonDefaultLootEvent;
import farn.farn_util.api.dungeon.event.DungeonDefaultMobEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class NormalDungeonImpl implements DungeonImpl{
    private final ArrayList<String> mobs = new ArrayList<>();
    private final ArrayList<DungeonLoot> loot = new ArrayList<>();
    private final ArrayList<DungeonLoot> guaranteedLoot = new ArrayList<>();
    private boolean initMob = false;
    private boolean initLoot = false;

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

    public void removeAllMobs() {
        initMob = true;
        mobs.clear();
    }

    public void addDefaultMobs() {
        for(int i = 0; i < 10; ++i) {
            mobs.add("Skeleton");
        }

        for(int j = 0; j < 20; ++j) {
            mobs.add("Zombie");
        }

        for(int k = 0; k < 10; ++k) {
            mobs.add("Spider");
        }

        FarnUtil.setupEvent(new DungeonDefaultMobEvent());
    }

    public String getRandomMob(Random random) {
        if (!initMob) {
            addDefaultMobs();
            initMob = true;
        }

        return mobs.isEmpty() ? "Pig" : mobs.get((random).nextInt(mobs.size()));
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

    public void removeAllLoots() {
        initLoot = true;
        loot.clear();
        guaranteedLoot.clear();
    }

    public void addDefaultLoots() {
        for(int i = 0; i < 100; ++i) {
            loot.add(new DungeonLoot(new ItemStack(Item.SADDLE)));
        }

        for(int j = 0; j < 100; ++j) {
            loot.add(new DungeonLoot(new ItemStack(Item.IRON_INGOT), 1, 4));
        }

        for(int k = 0; k < 100; ++k) {
            loot.add(new DungeonLoot(new ItemStack(Item.BREAD)));
        }

        for(int l = 0; l < 100; ++l) {
            loot.add(new DungeonLoot(new ItemStack(Item.WHEAT), 1, 4));
        }

        for(int i1 = 0; i1 < 100; ++i1) {
            loot.add(new DungeonLoot(new ItemStack(Item.GUNPOWDER), 1, 4));
        }

        for(int j1 = 0; j1 < 100; ++j1) {
            loot.add(new DungeonLoot(new ItemStack(Item.STRING), 1, 4));
        }

        for(int k1 = 0; k1 < 100; ++k1) {
            loot.add(new DungeonLoot(new ItemStack(Item.BUCKET)));
        }

        loot.add(new DungeonLoot(new ItemStack(Item.GOLDEN_APPLE)));

        for(int l1 = 0; l1 < 50; ++l1) {
            loot.add(new DungeonLoot(new ItemStack(Item.REDSTONE), 1, 4));
        }

        for(int i2 = 0; i2 < 5; ++i2) {
            loot.add(new DungeonLoot(new ItemStack(Item.RECORD_THIRTEEN)));
        }

        for(int j2 = 0; j2 < 5; ++j2) {
            loot.add(new DungeonLoot(new ItemStack(Item.RECORD_CAT)));
        }

        FarnUtil.setupEvent(new DungeonDefaultLootEvent());
    }

    public ItemStack getRandomLoots(Random random) {
        if (!initLoot) {
            addDefaultLoots();
            initLoot = true;
        }

        return loot.isEmpty() ? null : loot.get((random).nextInt(loot.size())).getStack();
    }
}
