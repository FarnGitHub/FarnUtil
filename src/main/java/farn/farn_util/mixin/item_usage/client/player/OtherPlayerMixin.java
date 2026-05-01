package farn.farn_util.mixin.item_usage.client.player;

import farn.farn_util.mixin.item_usage.common.PlayerMixin;
import net.minecraft.client.network.OtherPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OtherPlayerEntity.class, priority = 900)
public abstract class OtherPlayerMixin extends PlayerMixin {
    @Unique
    private boolean farnutil_otherPlrUse = false;

    public OtherPlayerMixin(World world, String name) {
        super(world);
    }

    @Inject(method="tickMovement", at = @At("TAIL"))
    public void syncItemUsing(CallbackInfo ci) {
        if(!this.farnutil_otherPlrUse && this.farnutil_hasAction() && this.inventory.main[this.inventory.selectedSlot] != null) {
            ItemStack stack = this.inventory.main[this.inventory.selectedSlot];
            this.farnutil_setUsingItemMaxDuration(stack, stack.getItem().farnutil_getMaxDuration(stack));
            this.farnutil_otherPlrUse = true;
        } else if(this.farnutil_otherPlrUse && !this.farnutil_hasAction()) {
            this.farnutil_clearUsingItem();
            this.farnutil_otherPlrUse = false;
        }
    }
}
