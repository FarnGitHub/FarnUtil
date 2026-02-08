package farn.farn_util.impl.entity_model_flag;

import net.modificationstation.stationapi.api.util.Identifier;

@SuppressWarnings("unused")
public interface EntityModelFlagImpl {

    boolean modelflag_getModelFlag(Identifier id, boolean defaultValue);

    void modelflag_setModelFlag(Identifier id, boolean flag);
}
