package farn.farn_util.api.dungeon;

import farn.farn_util.impl.dungeon.DungeonWeight;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Random;

public class DungeonAPI {
	public static final ObjectArrayList<DungeonMonster> mobs = new ObjectArrayList<>();
	public static final ObjectArrayList<DungeonLoot> loots = new ObjectArrayList<>();
	private static int TOTAL_MOB_WEIGHT = 0;
	private static int TOTAL_LOOT_WEIGHT = 0;

	@SuppressWarnings("unused")
	public static DungeonMonster addMob(String mob) {
		return addMob(mob, 100);
	}

	public static DungeonMonster addMob(String mob, int weight) {
		DungeonMonster dungeonmonster = new DungeonMonster(mob, weight);
		mobs.add(dungeonmonster);
		dungeonmonster.index = mobs.indexOf(dungeonmonster);
		TOTAL_MOB_WEIGHT = getTotalWeight(mobs, 10);
		return dungeonmonster;
	}

	public static DungeonLoot addLoot(DungeonLoot loot) {
		loots.add(loot);
		loot.index = loots.indexOf(loot);
		TOTAL_LOOT_WEIGHT = getTotalWeight(loots, 100);
		return loot;
	}

	@SuppressWarnings("unused")
	public static DungeonLoot addLoot(DungeonLoot loot, int weight) {
		loot.weight = weight;
		return addLoot(loot);
	}

	public static ItemStack pickLoot(Random random) {
		if (TOTAL_LOOT_WEIGHT > 0) {
			int weight = random.nextInt(TOTAL_LOOT_WEIGHT);

			for (DungeonLoot loot : loots) {
				weight -= loot.weight;
				if (weight < 0 && loot.loot != null) {
					ItemStack lootItem = loot.loot.copy();
					lootItem.count = loot.min + random.nextInt(loot.max - loot.min + 1);
					return lootItem;
				}
			}
		}
		return null;
	}

	public static String pickMob(Random random) {
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

	public static int getTotalWeight(Collection<? extends DungeonWeight> list, int min) {
		int total = 0;
		for(DungeonWeight loot : list)
			total += loot.weight;
		if(total < min)
			total = min;
		return total;
	}
}
