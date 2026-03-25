package farn.farn_util.mixin.item_usage.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import farn.farn_util.api.item_usage.FovHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class MixinFovMulti {

    @Shadow
    private Minecraft client;

    @ModifyReturnValue(method="getFov", at = @At("RETURN"))
    public float test(float original) {
        return original *  FovHandler.getFovWithCheck(client.player);
    }
}
