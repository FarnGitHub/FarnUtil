package farn.farn_util.mixin.item_usage.client.player;

import farn.farn_util.api.item_usage.ActionType;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {

    @Inject(method="renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void dasdadsad(PlayerEntity player, float tick, CallbackInfo ci) {
        if(player.farnutil_isUsingItem()) {
            ActionType action = player.farnutil_getActionType(player.getHand());
            if(action != null) {
                action.applyThirdPersonItemRotation(tick, player, player.getHand());
            }
        }
    }
}
