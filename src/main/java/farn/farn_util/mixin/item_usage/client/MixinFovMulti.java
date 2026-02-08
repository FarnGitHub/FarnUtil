package farn.farn_util.mixin.item_usage.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import farn.farn_util.api.item_usage.FovHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinFovMulti {

    @Shadow
    private Minecraft client;

    @Inject(method="getFov", at = @At("RETURN"))
    public void overrideFov(float tick, CallbackInfoReturnable<Float> cir, @Local(index = 3) LocalFloatRef fov) {
        fov.set(fov.get() * FovHandler.getFovWithCheck(client.player));
    }
}
