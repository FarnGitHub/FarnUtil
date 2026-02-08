package farn.farn_util.mixin.item_usage.client;

import farn.farn_util.api.item_usage.ActionType;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArsenicOverlayRenderer.class)
public class OverlayItemRendererMixin {

    @Inject(method={"renderVanilla(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", "renderModel(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void renderHeldItem(float tick, float avgHeight, ClientPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(player.farnutil_isUsingItem()) {
            ActionType iden = player.farnutil_getActionType(stack);
            if(iden != null) iden.applyFirstPersonItemRotation(tick, avgHeight, player, stack);
        }
    }
}
