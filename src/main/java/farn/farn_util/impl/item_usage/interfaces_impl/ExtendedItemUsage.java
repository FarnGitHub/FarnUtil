package farn.farn_util.impl.item_usage.interfaces_impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public interface ExtendedItemUsage {
    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     * @param stack the ItemStack instance of that item.
     * @param world the current world.
     * @param player The Player that used the item.
     * @param duration the current duration.
     */
    default void farnutil_stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration) {
    }

    /**
     * How long it takes to use or consume an item
     * @param stack the ItemStack instance of that item.
     */
    default int farnutil_getMaxDuration(ItemStack stack) {
        return 0;
    }

    /**
     * returns the action that specified what the item does when using it
     * useful for player animation
     */
    default Identifier farnutil_getActionType() {
        return null;
    }

    /**
     * returns the action that specified what the item does when using it
     * useful for player animation
     * @param stack the ItemStack instance of that item.
     */
    default Identifier farnutil_getActionType(ItemStack stack) {
        return farnutil_getActionType();
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.).
     * Not called when the player stops using the Item before the action is complete.
     * @param stack the ItemStack instance of that item.
     */
    default ItemStack farnutil_finishUsingItem(ItemStack stack, World world, PlayerEntity player) {
        return stack;
    }

    /**
     * Gets the player's field of view multiplier when using item (E.g. bow zooming)
     * @param entity The Player that using the item.
     * @param stack the ItemStack instance of that item.
     * @param duration the current duration.
     */
    default float farnutil_getFovMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return 1.0F;
    }

    /**
     * Gets the player's walking speed multiplier when using item (E.g. bow's slowdown)
     * @param entity The Player that using the item.
     * @param stack the ItemStack instance of that item.
     * @param duration the current duration.
     */
    default float farnutil_getSpeedMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return 1.0F;
    }
}
