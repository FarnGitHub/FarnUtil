package farn.farn_util.mixin.particle;

import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.impl.particle.CustomPaticleImpl;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
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

    /*@Inject(method = "render", at = @At("TAIL"))
    void render(Entity entity, float partialTicks, CallbackInfo ci, @Local(ordinal = 1)float var3, @Local(ordinal = 2)float var4, @Local(ordinal = 3)float var5, @Local(ordinal = 4)float var6, @Local(ordinal = 5)float var7){
        CustomPaticleImpl.render(partialTicks, var3, var4, var5, var6, var7);
    }*/
}
