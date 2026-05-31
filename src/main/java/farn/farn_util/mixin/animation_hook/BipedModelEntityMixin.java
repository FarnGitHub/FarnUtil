package farn.farn_util.mixin.animation_hook;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.animation_hook.bipedmodel.BipedModelEvent;
import farn.farn_util.api.dungeon.event.DungeonDefaultLootEvent;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.modificationstation.stationapi.api.StationAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedModelEntityMixin {

    @Inject(method="render", at = @At("TAIL"))
    public void renderModel(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        BipedModelEvent.Render event = new BipedModelEvent.Render();
        event.setVar((BipedEntityModel) (Object) this, limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        FarnUtil.setupEvent(event);
    }

    @Inject(method="setAngles", at = @At("TAIL"))
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        BipedModelEvent.SetAngle event = new BipedModelEvent.SetAngle();
        event.setVar((BipedEntityModel) (Object) this, limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        FarnUtil.setupEvent(event);
    }
}
