package farn.farn_util.api.dungeon;

import farn.farn_util.impl.dungeon.DungeonImpl;
import farn.farn_util.impl.dungeon.NormalDungeonImpl;
import farn.farn_util.impl.dungeon.SapiDungeonImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class DungeonAPI {
	public static final DungeonImpl impl =
			FabricLoader.getInstance().isModLoaded("shockahpi") ?
					 new SapiDungeonImpl() : new NormalDungeonImpl();

	@SuppressWarnings("unused")
	public static void addMob(String mob) {
		addMob(mob, 10);
	}

	public static void addMob(String mob, int amount) {
		impl.addMob(mob, amount);
	}

	@SuppressWarnings("unused")
	public static void removeMob(String s) {
		impl.removeMob(s);
	}

	public static String getRandomMob(Random random) {
		return impl.getRandomMob(random);
	}

	@SuppressWarnings("unused")
	public static void addLoot(DungeonLoot dungeonloot) {
		addLoot(dungeonloot, 100);
		impl.addLoot(dungeonloot, 100);
	}

	public static void addLoot(DungeonLoot dungeonloot, int amount) {
		impl.addLoot(dungeonloot, amount);

	}

	@SuppressWarnings("unused")
	public static void addGuaranteedLoot(DungeonLoot dungeonloot) {
		impl.addGuaranteedLoot(dungeonloot);
	}


	public static int getGuaranteedLootSize() {
		return impl.getGuaranteedLootSize();
	}


	public static DungeonLoot getGuaranteedLoot(int i) {
		return impl.getGuaranteedLoot(i);
	}

	@SuppressWarnings("unused")
	public static void removeLoot(int i) {
		impl.removeLoot(i);
	}

	public static ItemStack getRandomLoots(Random random) {
		return impl.getRandomLoots(random);
	}
}
