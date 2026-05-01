package farn.farn_util.mixin.item_usage.common;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.item_usage.ActionHandler;
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
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Util;
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
    ItemStack useApi_itemStack;
    @Unique
    int useApi_holdingDuration;
    @Unique
    boolean useApi_hasAction = false;

    public PlayerMixin(World world) {
        super(world);
    }

    public ItemStack farnutil_getUsingItem() {
        return useApi_itemStack;
    }

    public void farnutil_setUsingItem(ItemStack stack) {
        useApi_itemStack = stack;
    }

    public int farnutil_getUsingDuration() {
        return useApi_holdingDuration;
    }

    public void farnutil_setUsingDuration(int value) {
        useApi_holdingDuration = value;
    }

    public void farnutil_stopUsingItem() {
        if(farnutil_isUsingItem() && farnutil_getUsingItem().getItem() != null) {
            farnutil_getUsingItem().getItem().farnutil_stopUsingItem(farnutil_getUsingItem(), this.world, (PlayerEntity) (Object)this, farnutil_getUsingDuration());
        }
        farnutil_clearUsingItem();
    }

    public void farnutil_setUsingItemMaxDuration(ItemStack stack, int duration) {
        if(stack != useApi_itemStack) {
            useApi_itemStack = stack;
            useApi_holdingDuration = duration;
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
            int i1 = farnutil_getUsingItem().count;
            ItemStack itemStack2 = farnutil_getUsingItem().getItem().farnutil_finishUsingItem(useApi_itemStack, this.world, (PlayerEntity) (Object)this);
            if(itemStack2 != this.useApi_itemStack || itemStack2 != null && itemStack2.count != i1) {
                this.inventory.main[this.inventory.selectedSlot] = itemStack2;
                if(itemStack2.count == 0) {
                    this.inventory.main[this.inventory.selectedSlot] = null;
                }
            }

            this.farnutil_clearUsingItem();
        }
    }

    @Deprecated
    @Override
    public ActionHandler farnutil_getActionType(ItemStack stack) {
        if(stack != null && stack.getItem() != null) {
            return stack.getItem().farnutil_getActionType(stack);
        }
        return null;
    }

    @Inject(method="tick", at = @At("HEAD"))
    public void tickingWhatEver(CallbackInfo ci) {
        if(useApi_itemStack != null) {
            ItemStack itemStack = this.getHand();
            if(itemStack == useApi_itemStack) {
               if(--this.useApi_holdingDuration == 0 && !this.world.isRemote) {
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
        return useApi_hasAction;
    }

    public void farnutil_setHasAction(boolean value) {
        if(!world.isRemote) {
            useApi_hasAction = value;
            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
                MessagePacket packet = new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_actionUpdated"));
                packet.booleans = new boolean[]{value};
                packet.ints = new int[]{this.id};
                PacketHelper.sendToAllTracking(this, packet);
            }
        }
    }

    public void farnutil_setHasActionOnly(boolean value) {
        useApi_hasAction = value;
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
