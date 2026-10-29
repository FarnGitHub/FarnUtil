package farn.farn_util.api.dungeon;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DungeonAPI {
    private static final List<DungeonLootHandler> loots = new ArrayList<>();
    private static final List<DungeonMobHandler> mobs = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void registerLootHandler(DungeonLootHandler lootHandler) {
        loots.add(lootHandler);
    }

    @SuppressWarnings("unused")
    public static void registerMobHandler(DungeonMobHandler mobHandler) {
        mobs.add(mobHandler);
    }

    public static ItemStack getLoot(ItemStack itemStack, Random random, World world) {
        if(!loots.isEmpty())
            for(DungeonLootHandler lootHandler : loots)
                itemStack = lootHandler.getRandomLoot(itemStack, random, world);
        return itemStack;
    }

    public static String getMob(String original, Random random, World world) {
        if(!mobs.isEmpty())
            for (DungeonMobHandler mobHandles : mobs)
                original = Objects.requireNonNullElse(mobHandles.getRandomMob(original, random, world), original);
        return original;
    }
}
