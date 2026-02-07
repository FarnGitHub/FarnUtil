package farn.farn_util.impl.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Util;

import javax.annotation.Nullable;

public interface PlayerItemUsage {

    default ItemStack UseApi_getUsingItem() {
        return Util.assertImpl();
    }

    default boolean UseApi_hasUsingItem() {
        return UseApi_getUsingItem() != null;
    }

    default void UseApi_setUsingItem(ItemStack stack) {
        Util.assertImpl();
    }

    default void UseApi_setUsingItemWithDuration(ItemStack stack) {
        Util.assertImpl();
    }

    default int UseApi_getUsingDuration() {
        return Util.assertImpl();
    }

    default void UseApi_setUsingDuration(int value) {
        Util.assertImpl();
    }

    default void UseApi_stopUsingItem() {
        Util.assertImpl();
    }

    default void UseApi_clearUsingItem() {
        Util.assertImpl();
    }

    default void UseApi_finishedUsingItem() {
        Util.assertImpl();
    }

    default @Nullable String UseApi_getUsingItemAction(ItemStack stack) {
        return Util.assertImpl();
    }

    @Environment(EnvType.CLIENT)
    default float UseApi_getFovMultiplier() {
        return Util.assertImpl();
    }

    default float UseApi_getWalkSpeedMultiplier() {
        return Util.assertImpl();
    }

    default boolean UseApi_hasAction() {
        return Util.assertImpl();
    }
    default void UseApi_setHasAction(boolean value) {
        Util.assertImpl();
    }

    default void UseApi_setHasActionOnly(boolean value) {
        Util.assertImpl();
    }
}
