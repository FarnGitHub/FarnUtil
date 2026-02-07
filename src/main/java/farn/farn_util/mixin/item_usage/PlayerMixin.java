package farn.farn_util.mixin.item_usage;

import farn.farn_util.impl.item_usage.ItemUsageAPI;
import farn.farn_util.impl.item_usage.PlayerItemUsage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    @Unique
    ItemStack useApi_itemStack;
    @Unique
    int useApi_holdingDuration;
    @Unique
    boolean useApi_hasAction = false;

    public PlayerMixin(World world) {
        super(world);
    }

    public ItemStack UseApi_getUsingItem() {
        return useApi_itemStack;
    }

    public void UseApi_setUsingItem(ItemStack stack) {
        useApi_itemStack = stack;
    }

    public int UseApi_getUsingDuration() {
        return useApi_holdingDuration;
    }

    public void UseApi_setUsingDuration(int value) {
        useApi_holdingDuration = value;
    }

    public void UseApi_stopUsingItem() {
        if(UseApi_hasUsingItem() && UseApi_getUsingItem().getItem() != null) {
            UseApi_getUsingItem().getItem().UseApi_stopUsingItem(UseApi_getUsingItem(), this.world, (PlayerEntity) (Object)this, UseApi_getUsingDuration());
        }
        UseApi_clearUsingItem();
    }

    public void UseApi_setUsingItemWithDuration(ItemStack stack) {
        if(stack != UseApi_getUsingItem()) {
            UseApi_setUsingItem(stack);
            if(stack != null && stack.getItem() != null) {
                UseApi_setUsingDuration(stack.getItem().UseApi_getMaxDuration(stack));
            }
            UseApi_setHasAction(true);
        }
    }

    public void UseApi_clearUsingItem() {
        UseApi_setUsingItem(null);
        UseApi_setUsingDuration(0);
        UseApi_setHasAction(false);
    }

    public void UseApi_finishedUsingItem() {
        if(UseApi_hasUsingItem() && UseApi_getUsingItem().getItem() != null) {
            int i1 = UseApi_getUsingItem().count;
            ItemStack itemStack2 = UseApi_getUsingItem().getItem().UseApi_onFinishedUsing(useApi_itemStack, this.world, (PlayerEntity) (Object)this);
            if(itemStack2 != this.useApi_itemStack || itemStack2 != null && itemStack2.count != i1) {
                this.inventory.main[this.inventory.selectedSlot] = itemStack2;
                if(itemStack2.count == 0) {
                    this.inventory.main[this.inventory.selectedSlot] = null;
                }
            }

            this.UseApi_clearUsingItem();
        }
    }

    @Override
    public String UseApi_getUsingItemAction(ItemStack stack) {
        if(stack != null && stack.getItem() != null) {
            String str = stack.getItem().UseApi_getActionType(stack);
            if(str != null) return str;
        }
        return "";
    }

    @Inject(method="tick", at = @At("TAIL"))
    public void tickingWhatEver(CallbackInfo ci) {
        if(UseApi_hasUsingItem()) {
            ItemStack itemStack1 = this.inventory.getSelectedItem();
            if(!itemStack1.equals(this.useApi_itemStack)) {
                this.UseApi_clearUsingItem();
            } else {
                if(--this.useApi_holdingDuration == 0 && !this.world.isRemote) {
                    this.UseApi_finishedUsingItem();
                }
            }
        }
    }

    public float UseApi_getWalkSpeedMultiplier() {
        return UseApi_getUsingItem() != null ? UseApi_getUsingItem().getItem().UseApi_getWalkSpeedMultiplier((PlayerEntity) (Object) this, UseApi_getUsingItem(), UseApi_getUsingDuration()) : 1.0F;
    }

    @Environment(EnvType.CLIENT)
    public float UseApi_getFovMultiplier() {
        return 1.0F;
    }

    public boolean UseApi_hasAction() {
        return useApi_hasAction;
    }

    public void UseApi_setHasAction(boolean value) {
        if(!world.isRemote) {
            useApi_hasAction = value;
        } else {
            MessagePacket packet = new MessagePacket(ItemUsageAPI.NAMESPACE.id("item_usage_api_actionUpdated"));
            packet.booleans = new boolean[]{value};
            PacketHelper.sendToAllTracking(this, packet);
        }
    }

    public void UseApi_setHasActionOnly(boolean value) {
        useApi_hasAction = value;
    }

}
