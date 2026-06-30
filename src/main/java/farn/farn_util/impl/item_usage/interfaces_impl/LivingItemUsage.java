package farn.farn_util.impl.item_usage.interfaces_impl;

import net.modificationstation.stationapi.api.util.Util;

public interface LivingItemUsage {

    default boolean farnutil_hasAction() {
        return Util.assertImpl();
    }

    default void farnutil_setHasAction(boolean value) {
        Util.assertImpl();
    }
}
