package farn.farn_util.api.dungeon;

import farn.farn_util.impl.dungeon.DungeonImpl;
import farn.farn_util.impl.dungeon.NormalDungeonImpl;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class DungeonAPI {
	public static final DungeonImpl impl = new NormalDungeonImpl();

	@SuppressWarnings("unused")
	public static void addMob(String mob) {
		addMob(mob, 1);
	}

	public static void addMob(String mob, int amount) {
		impl.addMob(mob, amount);
	}

	public static String pickMob(Random random) {
		return impl.getRandomMob(random);
	}

	public static void addLoot(DungeonLoot loot) {
		impl.addLoot(loot);
	}

	@SuppressWarnings("unused")
	public static void addLoot(DungeonLoot loot, int amount) {
		loot.weight = amount;
		addLoot(loot);
	}

	public static ItemStack pickLoot(Random random) {
		return impl.getRandomLoots(random);
	}
}
