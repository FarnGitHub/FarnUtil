package farn.farn_util.mixin.item_usage;

import net.minecraft.client.network.OtherPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OtherPlayerEntity.class, priority = 900)
public abstract class OtherPlayerMPMixin extends PlayerEntity {
    @Unique
    private boolean UseApi_otherisItemInUse = false;

    public OtherPlayerMPMixin(World world, String name) {
        super(world);
    }

    @Inject(method="tickMovement", at = @At("TAIL"))
    public void syncItemUsing(CallbackInfo ci) {
        if(!this.UseApi_otherisItemInUse && this.farnutil_hasAction() && this.inventory.main[this.inventory.selectedSlot] != null) {
            this.farnutil_setUsingItemMaxDuration(this.inventory.main[this.inventory.selectedSlot]);
            this.UseApi_otherisItemInUse = true;
        } else if(this.UseApi_otherisItemInUse && !this.farnutil_hasAction()) {
            this.farnutil_clearUsingItem();
            this.UseApi_otherisItemInUse = false;
        }
    }
}
