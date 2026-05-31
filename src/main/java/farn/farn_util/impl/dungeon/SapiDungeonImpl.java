package farn.farn_util.impl.dungeon;

import farn.farn_util.api.dungeon.DungeonLoot;
import net.minecraft.item.ItemStack;
import shockahpi.SAPI;

import java.util.Random;

public class SapiDungeonImpl implements DungeonImpl{
    @Override
    public void addMob(String mob, int amount) {
        SAPI.dungeonAddMob(mob, amount);
    }

    @Override
    public void removeMob(String mob) {
        SAPI.dungeonRemoveMob(mob);

    }

    @Override
    public void removeAllMobs() {
        SAPI.dungeonRemoveAllMobs();

    }

    @Override
    public String getRandomMob(Random random) {
        return SAPI.dungeonGetRandomMob();
    }

    @Override
    public void addLoot(DungeonLoot dungeonloot, int amount) {
        SAPI.dungeonAddItem(convert(dungeonloot), amount);

    }

    @Override
    public void addGuaranteedLoot(DungeonLoot loot) {
        SAPI.dungeonAddGuaranteedItem(convert(loot));
    }

    @Override
    public int getGuaranteedLootSize() {
        return SAPI.dungeonGetAmountOfGuaranteed();
    }

    @Override
    public DungeonLoot getGuaranteedLoot(int amount) {
        return convert(SAPI.dungeonGetGuaranteed(amount));
    }

    @Override
    public void removeLoot(int target) {
        SAPI.dungeonRemoveItem(target);
    }

    @Override
    public void removeAllLoots() {
        SAPI.dungeonRemoveAllItems();
    }

    @Override
    public ItemStack getRandomLoots(Random random) {
        return SAPI.dungeonGetRandomItem();
    }

    public shockahpi.DungeonLoot convert(DungeonLoot l) {
        return new shockahpi.DungeonLoot(l.loot, l.min, l.max);
    }

    public DungeonLoot convert(shockahpi.DungeonLoot l) {
        return new DungeonLoot(l.loot, l.min, l.max);
    }
}
