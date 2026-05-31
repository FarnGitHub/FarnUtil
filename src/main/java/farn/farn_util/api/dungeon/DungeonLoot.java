package farn.farn_util.api.dungeon;

import farn.farn_util.impl.dungeon.DungeonWeight;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
public class DungeonLoot extends DungeonWeight {
	public ItemStack loot;
	public int min = 1;
	public int max = 1;

	public DungeonLoot(ItemStack stack) {
		super(100);
		this.loot = stack;
	}

	public DungeonLoot(ItemStack stack, int weight) {
		this(stack);
		this.weight = weight;
	}

	public DungeonLoot(ItemStack stack, int min, int max) {
		this(stack);
		this.min = min;
		this.max = max;
	}

	public DungeonLoot(ItemStack stack, int min, int max, int weight) {
		this(stack, min, max);
		this.weight = weight;
	}

	@Override
	public boolean remove() {
		if(index >= 0) {
			DungeonAPI.loots.remove(index);
			index = -1;
			return true;
		} else
			return false;
	}
}
