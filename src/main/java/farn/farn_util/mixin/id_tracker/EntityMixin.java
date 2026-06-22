package farn.farn_util.mixin.id_tracker;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.impl.id_tracker.EntityIDTrackerImpl;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityMixin implements EntityIDTrackerImpl {
    IDDataTracker tracker = new IDDataTracker();

    public IDDataTracker farnutil_getIdDataTracker() {
        return tracker;
    }
}
