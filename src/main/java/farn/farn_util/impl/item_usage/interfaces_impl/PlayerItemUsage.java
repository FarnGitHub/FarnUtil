package farn.farn_util.impl.item_usage.interfaces_impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Util;

import javax.annotation.Nullable;

public interface PlayerItemUsage {

    default ItemStack farnutil_getUsingItem() {
        return Util.assertImpl();
    }

    default boolean farnutil_isUsingItem() {
        return farnutil_getUsingItem() != null;
    }

    default void farnutil_setUsingItem(ItemStack stack) {
        Util.assertImpl();
    }

    default void farnutil_setUsingItemMaxDuration(ItemStack stack) {
        Util.assertImpl();
    }

    default int farnutil_getUsingDuration() {
        return Util.assertImpl();
    }

    default void farnutil_setUsingDuration(int value) {
        Util.assertImpl();
    }

    default void farnutil_stopUsingItem() {
        Util.assertImpl();
    }

    default void farnutil_clearUsingItem() {
        Util.assertImpl();
    }

    default void farnutil_finishUsingItem() {
        Util.assertImpl();
    }

    default @Nullable String farnutil_getActionType(ItemStack stack) {
        return Util.assertImpl();
    }

    @SuppressWarnings("all")
    default boolean farnutil_isActionType(ItemStack stack, String name) {
        try {
            return farnutil_getActionType(stack).equals(name);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    default float farnutil_getFovMultiplier() {
        return Util.assertImpl();
    }

    default float farnutil_getWalkSpeedMultiplier() {
        return Util.assertImpl();
    }

    default boolean farnutil_hasAction() {
        return Util.assertImpl();
    }
    default void farnutil_setHasAction(boolean value) {
        Util.assertImpl();
    }

    default void farnutil_setHasActionOnly(boolean value) {
        Util.assertImpl();
    }
}
