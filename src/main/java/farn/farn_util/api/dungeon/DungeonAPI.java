package farn.farn_util.api.dungeon;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.dungeon.event.DungeonDefaultLootEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class DungeonAPI {
	private static final ArrayList<String> mobs = new ArrayList<>();
	private static final ArrayList<DungeonLoot> loot = new ArrayList<>();
	private static final ArrayList<DungeonLoot> guaranteedLoot = new ArrayList<>();
	private static boolean initMob = false;
	private static boolean initLoot = false;

	@SuppressWarnings("unused")
	public static void addMob(String s) {
		addMob(s, 10);
	}

	public static void addMob(String s, int i) {
		for(int j = 0; j < i; ++j) {
			mobs.add(s);
		}

	}

	@SuppressWarnings("unused")
	public static void removeMob(String s) {
		for(int i = 0; i < mobs.size(); ++i) {
			if (mobs.get(i).equals(s)) {
				mobs.remove(i);
				--i;
			}
		}

	}

	@SuppressWarnings("unused")
	public static void removeAllMobs() {
		initMob = true;
		mobs.clear();
	}

	public static void addDefaultMobs() {
		for(int i = 0; i < 10; ++i) {
			mobs.add("Skeleton");
		}

		for(int j = 0; j < 20; ++j) {
			mobs.add("Zombie");
		}

		for(int k = 0; k < 10; ++k) {
			mobs.add("Spider");
		}

	}

	public static String getRandomMob(Random random) {
		if (!initMob) {
			addDefaultMobs();
			initMob = true;
		}

		return mobs.isEmpty() ? "Pig" : mobs.get((random).nextInt(mobs.size()));
	}

	@SuppressWarnings("unused")
	public static void addLoot(DungeonLoot dungeonloot) {
		addLoot(dungeonloot, 100);
	}

	public static void addLoot(DungeonLoot dungeonloot, int i) {
		for(int j = 0; j < i; ++j) {
			loot.add(dungeonloot);
		}

	}

	@SuppressWarnings("unused")
	public static void addGuaranteedLoot(DungeonLoot dungeonloot) {
		guaranteedLoot.add(dungeonloot);
	}


	public static int getGuaranteedLootSize() {
		return guaranteedLoot.size();
	}


	public static DungeonLoot getGuaranteedLoot(int i) {
		return guaranteedLoot.get(i);
	}

	@SuppressWarnings("unused")
	public static void removeLoot(int i) {
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

	@SuppressWarnings("unused")
	public static void removeAllLoots() {
		initLoot = true;
		loot.clear();
		guaranteedLoot.clear();
	}

	static void addDefaultLoots() {
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

	public static ItemStack getRandomLoots(Random random) {
		if (!initLoot) {
			addDefaultLoots();
			initLoot = true;
		}

		return loot.isEmpty() ? null : loot.get((random).nextInt(loot.size())).getStack();
	}
}
