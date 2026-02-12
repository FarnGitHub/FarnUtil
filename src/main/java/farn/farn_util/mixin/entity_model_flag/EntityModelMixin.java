package farn.farn_util.mixin.entity_model_flag;

import farn.farn_util.impl.entity_model_flag.EntityModelFlagImpl;
import it.unimi.dsi.fastutil.objects.Reference2BooleanOpenHashMap;
import net.minecraft.client.render.entity.model.EntityModel;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(EntityModel.class)
public class EntityModelMixin implements EntityModelFlagImpl {
    @Unique
    Reference2BooleanOpenHashMap<Identifier> modelflag_actionMap = new Reference2BooleanOpenHashMap<>();

    @Override
    public boolean modelflag_getModelFlag(Identifier id, boolean defaultValue) {
        return modelflag_actionMap.computeIfAbsent(id, k -> defaultValue);
    }

    @Override
    public void modelflag_setModelFlag(Identifier id, boolean flag) {
        modelflag_actionMap.put(id, flag);
    }
}
