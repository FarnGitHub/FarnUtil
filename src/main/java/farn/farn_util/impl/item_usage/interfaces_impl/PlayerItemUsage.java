package farn.farn_util.impl.item_usage.interfaces_impl;

import farn.farn_util.api.item_usage.ActionHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Util;

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

    default void farnutil_setUsingItemMaxDuration(ItemStack stack, int duration) {
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

    @Deprecated
    default ActionHandler farnutil_getActionType(ItemStack stack) {
        return Util.assertImpl();
    }

    @Deprecated
    default boolean farnutil_hasActionType(ItemStack stack) {
        return farnutil_getActionType(stack) != null;
    }

    @Deprecated
    default boolean farnutil_isActionType(ItemStack stack, Identifier identifier) {
        try {
            return farnutil_getActionType(stack).id.equals(identifier);
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
