package farn.farn_util.mixin.item_usage.common;

import farn.farn_util.impl.item_usage.ItemUsageImplServer;
import farn.farn_util.impl.item_usage.interfaces_impl.PlayerItemUsage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin implements PlayerItemUsage {
    @Shadow
    public PlayerInventory inventory;

    @Shadow
    public ItemStack getHand() {
        throw new AssertionError();
    }

    @Unique
    ItemStack farnutil_stack;
    @Unique
    int farnutil_holdDuration;

    public ItemStack farnutil_getUsingItem() {
        return farnutil_stack;
    }

    public void farnutil_setUsingItem(ItemStack stack) {
        farnutil_stack = stack;
    }

    public int farnutil_getUsingDuration() {
        return farnutil_holdDuration;
    }

    public void farnutil_setUsingDuration(int value) {
        farnutil_holdDuration = value;
    }

    public void farnutil_stopUsingItem() {
        if(farnutil_isUsingItem() && farnutil_getUsingItemAsItem() != null)
            farnutil_getUsingItemAsItem().farnutil_stopUsingItem(farnutil_getUsingItem(), farnutil_self().world, farnutil_self(), farnutil_getUsingDuration());
        farnutil_clearUsingItem();
    }

    public void farnutil_setUsingItemMaxDuration(ItemStack stack, int duration) {
        if(stack != farnutil_stack) {
            farnutil_stack = stack;
            farnutil_holdDuration = duration;
            farnutil_self().farnutil_setHasAction(true);
        }
    }

    public void farnutil_clearUsingItem() {
        farnutil_setUsingItem(null);
        farnutil_setUsingDuration(0);
        farnutil_self().farnutil_setHasAction(false);
    }

    public void farnutil_finishUsingItem() {
        if(farnutil_isUsingItem()) {
            farnutil_getUsingItemAsItem().farnutil_usingTick(farnutil_self(), farnutil_stack, this.farnutil_holdDuration, true);
            int count = farnutil_getUsingItem().count;
            ItemStack stack = farnutil_getUsingItem().getItem().farnutil_finishUsingItem(farnutil_stack, farnutil_self().world, farnutil_self());
            if(stack != this.farnutil_stack || stack != null && stack.count != count) {
                this.inventory.main[this.inventory.selectedSlot] = stack;
                if(stack.count == 0) {
                    this.inventory.main[this.inventory.selectedSlot] = null;
                }
            }

            this.farnutil_clearUsingItem();
        }
    }

    public Item farnutil_getUsingItemAsItem() {
        return this.farnutil_isUsingItem() ? this.farnutil_getUsingItem().getItem() : null;
    }

    @Inject(method="tick", at = @At("HEAD"))
    public void farnutil_tick(CallbackInfo ci) {
        if(farnutil_stack != null) {
            ItemStack itemStack = this.getHand();
            if(itemStack == farnutil_stack) {
                farnutil_getUsingItemAsItem().farnutil_usingTick((PlayerEntity) (Object) this, farnutil_stack, this.farnutil_holdDuration, false);
                if(--this.farnutil_holdDuration == 0 && !farnutil_self().world.isRemote) {
                    this.farnutil_finishUsingItem();
                }
            } else {
                this.farnutil_clearUsingItem();
            }

        }
    }

    public float farnutil_getWalkSpeedMultiplier() {
        return farnutil_getUsingItem() != null ? farnutil_getUsingItem().getItem().farnutil_getSpeedMultiplier((PlayerEntity) (Object) this, farnutil_getUsingItem(), farnutil_getUsingDuration()) : 1.0F;
    }

    @Environment(EnvType.CLIENT)
    public float farnutil_getFovMultiplier() {
        return 1.0F;
    }

    public boolean farnutil_hasActionId(String str) {
        return farnutil_getActionId() != null && farnutil_getActionId().equals(str);
    }

    public String farnutil_getActionId() {
        if(getHand() == null || getHand().getItem() == null) return null;
        return getHand().getItem().farnutil_getActionId(farnutil_self(),getHand());
    }

    @SuppressWarnings("MissingUnique")
    protected PlayerEntity farnutil_self() {
        return (PlayerEntity)(Object) this;
    }

}
