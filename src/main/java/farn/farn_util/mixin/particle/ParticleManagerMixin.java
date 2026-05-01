package farn.farn_util.mixin.particle;

import farn.farn_util.impl.particle.CustomPaticleImpl;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(method = "removeDeadParticles", at = @At("TAIL"))
    void removeDeadParticle(CallbackInfo ci){
        CustomPaticleImpl.tick();
    }
    @Inject(method = "setWorld", at = @At("TAIL"))
    void setWorld(World world, CallbackInfo ci){
        CustomPaticleImpl.clear();
    }
}
