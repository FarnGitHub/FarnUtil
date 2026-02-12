package farn.farn_util.mixin.item_usage.client.player;

import farn.farn_util.api.item_usage.ActionHandler;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {

    @Inject(method="renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void applyThirdPersonRotation(PlayerEntity player, float tick, CallbackInfo ci) {
        if(player.farnutil_isUsingItem()) {
            ActionHandler action = player.farnutil_getActionType(player.getHand());
            if(action != null && action.animation != null) {
                action.animation.applyThirdPersonItemRotation(tick, player, player.getHand());
            }
        }
    }

    @Inject(method="render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;DDDFF)V", shift = At.Shift.BEFORE))
    public void beforeRenderEntity(PlayerEntity player, double d, double e, double f, float g, float h, CallbackInfo ci) {
        if(player.farnutil_isUsingItem()) {
            ActionHandler action = player.farnutil_getActionType(player.getHand());
            if(action != null && action.animation != null) {
                action.animation.beforePlayerRender((PlayerEntityRenderer)(Object)this,player,player.getHand(),d,e,f,g,h);
            }
        }
    }

    @Inject(method="render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;DDDFF)V", shift = At.Shift.AFTER))
    public void afterRenderEntity(PlayerEntity player, double d, double e, double f, float g, float h, CallbackInfo ci) {
        if(player.farnutil_isUsingItem()) {
            ActionHandler action = player.farnutil_getActionType(player.getHand());
            if(action != null && action.animation != null) {
                action.animation.afterPlayerRender((PlayerEntityRenderer)(Object)this,player,player.getHand(),d,e,f,g,h);
            }
        }
    }
}
