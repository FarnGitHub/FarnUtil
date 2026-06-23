package farn.farn_util.mixin.id_tracker;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.impl.id_tracker.EntityIDTrackerImpl;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements EntityIDTrackerImpl {
    IDDataTracker farnutil_Idtracker = new IDDataTracker();

    public IDDataTracker farnutil_getIdDataTracker() {
        return farnutil_Idtracker;
    }

    @Inject(method="<init>", at = @At("TAIL"))
    public void farnutil_idTrackerInit(World world, CallbackInfo ci) {
        farnutil_initIdDataTracker();
        int test = farnutil_getIdDataTracker().get("test");
    }
}
