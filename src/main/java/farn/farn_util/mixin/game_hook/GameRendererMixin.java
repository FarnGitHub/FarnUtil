package farn.farn_util.mixin.game_hook;

import farn.farn_util.api.animation_hook.bipedmodel.BipedModelEvent;
import farn.farn_util.api.game_hook.RenderWorldLastEvent;
import farn.farn_util.impl.game_hook.RenderWorldLastImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.modificationstation.stationapi.api.StationAPI;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private Minecraft client;

    @Inject(method="renderFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;applyFog(IF)V", ordinal = 5, shift = At.Shift.BEFORE))
    public void beforeCloudRender(float tick, long time, CallbackInfo ci) {
        RenderWorldLastImpl.pushMatrixCloud();
    }

    @Inject(method="renderFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;applyFog(IF)V", ordinal = 6, shift = At.Shift.AFTER))
    public void afterCloudRender(float tick, long time, CallbackInfo ci) {
        RenderWorldLastImpl.popMatrixCloud(client.worldRenderer, tick);
    }
}
