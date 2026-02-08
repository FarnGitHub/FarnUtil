package farn.farn_util.impl.entity_model_flag;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Util;

@SuppressWarnings("unused")
public interface EntityModelFlagImpl {

    default boolean modelflag_getModelFlag(Identifier id, boolean defaultValue) {
        return Util.assertImpl();
    }

    default void modelflag_setModelFlag(Identifier id, boolean flag) {
        Util.assertImpl();
    }
}
