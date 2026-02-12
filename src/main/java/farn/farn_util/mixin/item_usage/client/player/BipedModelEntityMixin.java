package farn.farn_util.mixin.item_usage.client.player;

import farn.farn_util.api.biped_model_extended.BipedModelHandler;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedModelEntityMixin {

    @Inject(method="render", at = @At("TAIL"))
    public void renderModel(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        BipedModelHandler.loopRender((BipedEntityModel) (Object) this, limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
    }

    @Inject(method="setAngles", at = @At("TAIL"))
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        BipedModelHandler.loopAngles((BipedEntityModel) (Object) this, limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
    }
}
