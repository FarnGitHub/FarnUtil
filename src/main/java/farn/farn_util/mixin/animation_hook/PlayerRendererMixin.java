package farn.farn_util.mixin.animation_hook;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.animation_hook.player_render.PlayerRenderEvent;
import farn.farn_util.api.animation_hook.player_render.ThirdPersonItemRotationEvent;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.StationAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {

    @Inject(method="renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void applyThirdPersonRotation(PlayerEntity player, float tick, CallbackInfo ci) {
        ThirdPersonItemRotationEvent event = new ThirdPersonItemRotationEvent(
                (PlayerEntityRenderer) (Object) this,tick, player, player.getHand()
        );
        FarnUtil.setupEvent(event);
    }

    @Inject(method="render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;DDDFF)V", shift = At.Shift.BEFORE))
    public void beforeRenderEntity(PlayerEntity player, double d, double e, double f, float g, float h, CallbackInfo ci) {
        PlayerRenderEvent.Before event = new PlayerRenderEvent.Before();
        event.setVar((PlayerEntityRenderer)(Object)this,player,player.getHand(),d,e,f,g,h);
        FarnUtil.setupEvent(event);
    }

    @Inject(method="render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;DDDFF)V", shift = At.Shift.AFTER))
    public void afterRenderEntity(PlayerEntity player, double d, double e, double f, float g, float h, CallbackInfo ci) {
        PlayerRenderEvent.After event = new PlayerRenderEvent.After();
        event.setVar((PlayerEntityRenderer)(Object)this,player,player.getHand(),d,e,f,g,h);
        FarnUtil.setupEvent(event);
    }
}
