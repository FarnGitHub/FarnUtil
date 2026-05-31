package farn.farn_util.api.dungeon;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class DungeonLoot {
	public final ItemStack loot;
	public final int min;
	public final int max;

	public DungeonLoot(ItemStack stack) {
		this.loot = new ItemStack(stack.getItem(), 1, stack.getDamage());
		this.min = this.max = stack.count;
	}

	public DungeonLoot(ItemStack stack, int min, int max) {
		this.loot = new ItemStack(stack.getItem(), 1, stack.getDamage());
		this.min = min;
		this.max = max;
	}

	public ItemStack getStack(Random random) {
		return new ItemStack(this.loot.getItem(), this.min + (random).nextInt(this.max - this.min + 1), loot.getDamage());
	}
}
