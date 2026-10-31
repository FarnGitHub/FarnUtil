package farn.farn_util.mixin.item_usage.common;

import farn.farn_util.FarnUtil;
import farn.farn_util.impl.item_usage.interfaces_impl.LivingItemUsage;
import farn.farn_util.mixin.id_tracker.EntityMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin extends EntityMixin implements LivingItemUsage {

    @Inject(method="<init>", at = @At("TAIL"))
    public void farnutil_initAction(World world, CallbackInfo ci) {
        this.farnutil_getIdDataTracker().startTracking(FarnUtil.id("action"), false);
    }

    public boolean farnutil_hasAction() {
        return this.farnutil_getIdDataTracker().get(FarnUtil.NAMESPACE.id("action"));
    }

    public void farnutil_setHasAction(boolean value) {
        this.farnutil_getIdDataTracker().set(FarnUtil.NAMESPACE.id("action"), value);
    }
}
