package farn.farn_util.mixin.item_usage;

import farn.farn_util.impl.item_usage.registry.HeldItemRotationRegistry;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArsenicOverlayRenderer.class)
public class OverlayItemRendererMixin {

    @Inject(method={"renderVanilla(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", "renderModel(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void renderHeldItem(float tick, float avgHeight, ClientPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        Identifier iden = player.farnutil_getActionType(stack);
        if(iden != null)
            HeldItemRotationRegistry.instance.
                    invokeHeldItemRotations(iden, tick, avgHeight, player, stack);
    }
}
