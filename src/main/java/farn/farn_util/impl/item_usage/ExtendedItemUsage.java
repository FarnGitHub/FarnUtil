package farn.farn_util.impl.item_usage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ExtendedItemUsage {

    default void UseApi_stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration) {
    }

    default int UseApi_getMaxDuration(ItemStack stack) {
        return 0;
    }

    default String UseApi_getActionType(ItemStack stack) {
        return null;
    }

    default ItemStack UseApi_onFinishedUsing(ItemStack stack, World world, PlayerEntity player) {
        return stack;
    }

    default float UseApi_getFovMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return 1.0F;
    }

    default float UseApi_getWalkSpeedMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return 1.0F;
    }
}
