package farn.farn_util.mixin.animation_hook;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.animation_hook.player_render.FirstPersonItemRotationEvent;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicOverlayRenderer;
import net.modificationstation.stationapi.mixin.arsenic.client.class_556Accessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArsenicOverlayRenderer.class)
public class OverlayItemRendererMixin {

    @Shadow
    @Final
    private class_556Accessor access;

    @Inject(method={"renderVanilla(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", "renderModel(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void renderHeldItem(float tick, float avgHeight, ClientPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        FirstPersonItemRotationEvent event = new FirstPersonItemRotationEvent(
                (HeldItemRenderer)access,
                (ArsenicOverlayRenderer)(Object)this,
                tick,
                avgHeight,
                player,
                stack
        );
        FarnUtil.setupEvent(event);
    }
}
