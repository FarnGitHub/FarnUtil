package farn.farn_util.mixin.item_usage.common;

import farn.farn_util.FarnUtil;
import farn.farn_util.impl.item_usage.interfaces_impl.PlayerItemUsage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerItemUsage {
    @Shadow
    public PlayerInventory inventory;

    @Shadow
    public abstract ItemStack getHand();

    @Unique
    ItemStack farnutil_stack;
    @Unique
    int farnutil_holdDuration;
    @Unique
    boolean farnutil_hasAction = false;

    public PlayerMixin(World world) {
        super(world);
    }

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
        if(farnutil_isUsingItem() && farnutil_getUsingItem().getItem() != null) {
            farnutil_getUsingItem().getItem().farnutil_stopUsingItem(farnutil_getUsingItem(), this.world, (PlayerEntity) (Object)this, farnutil_getUsingDuration());
        }
        farnutil_clearUsingItem();
    }

    public void farnutil_setUsingItemMaxDuration(ItemStack stack, int duration) {
        if(stack != farnutil_stack) {
            farnutil_stack = stack;
            farnutil_holdDuration = duration;
            farnutil_setHasAction(true);
        }
    }

    public void farnutil_clearUsingItem() {
        farnutil_setUsingItem(null);
        farnutil_setUsingDuration(0);
        farnutil_setHasAction(false);
    }

    public void farnutil_finishUsingItem() {
        if(farnutil_isUsingItem()) {
            int count = farnutil_getUsingItem().count;
            ItemStack stack = farnutil_getUsingItem().getItem().farnutil_finishUsingItem(farnutil_stack, this.world, (PlayerEntity) (Object)this);
            if(stack != this.farnutil_stack || stack != null && stack.count != count) {
                this.inventory.main[this.inventory.selectedSlot] = stack;
                if(stack.count == 0) {
                    this.inventory.main[this.inventory.selectedSlot] = null;
                }
            }

            this.farnutil_clearUsingItem();
        }
    }

    @Inject(method="tick", at = @At("HEAD"))
    public void farnutil_tick(CallbackInfo ci) {
        if(farnutil_stack != null) {
            ItemStack itemStack = this.getHand();
            if(itemStack == farnutil_stack) {
               if(--this.farnutil_holdDuration == 0 && !this.world.isRemote) {
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

    public boolean farnutil_hasAction() {
        return farnutil_hasAction;
    }

    public void farnutil_setHasAction(boolean value) {
        if(!world.isRemote) {
            farnutil_hasAction = value;
            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
                MessagePacket packet = new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_actionUpdated"));
                packet.booleans = new boolean[]{value};
                packet.ints = new int[]{this.id};
                PacketHelper.sendToAllTracking(this, packet);
            }
        }
    }

    public void farnutil_setHasActionOnly(boolean value) {
        farnutil_hasAction = value;
    }

    public boolean farnutil_hasActionId(String str) {
        return farnutil_getActionId() != null && farnutil_getActionId().equals(str);
    }

    public String farnutil_getActionId() {
        try {
            return getHand().getItem().farnutil_getActionId(
                    (PlayerEntity)(Object) this,
                    getHand()
            );
        } catch (Exception e) {
            return null;
        }
    }

}
