package farn.farn_util.mixin.item_usage.server;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerNetworkHandlerMixin {

    @Definition(id = "player", field = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;player:Lnet/minecraft/entity/player/ServerPlayerEntity;")
    @Definition(id= "skipPacketSlotUpdates", field = "Lnet/minecraft/entity/player/ServerPlayerEntity;skipPacketSlotUpdates:Z")
    @Expression(value="this.player.skipPacketSlotUpdates = true")//,
    @WrapWithCondition(method="onPlayerInteractBlock", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean test(ServerPlayerEntity instance, boolean value,
                        @Local(type= ItemStack.class) ItemStack itemStack,
                        @Share(value="isNotItemWithDuration", namespace = "farn_util") LocalBooleanRef ref) {
        ref.set(itemStack == null || Item.ITEMS[itemStack.itemId].farnutil_getMaxDuration(itemStack) <= 0);
        return ref.get();
    }

    @Definition(id = "player", field = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;player:Lnet/minecraft/entity/player/ServerPlayerEntity;")
    @Definition(id= "skipPacketSlotUpdates", field = "Lnet/minecraft/entity/player/ServerPlayerEntity;skipPacketSlotUpdates:Z")
    @Expression(value="this.player.skipPacketSlotUpdates = false")//,
    @WrapWithCondition(method="onPlayerInteractBlock", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean test2(ServerPlayerEntity instance, boolean value,
                        @Share(value="isNotItemWithDuration", namespace = "farn_util") LocalBooleanRef ref) {
        return ref.get();
    }

    @Definition(id = "player", field = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;player:Lnet/minecraft/entity/player/ServerPlayerEntity;")
    @Definition(id = "inventory", field = "Lnet/minecraft/entity/player/ServerPlayerEntity;inventory:Lnet/minecraft/entity/player/PlayerInventory;")
    @Definition(id = "main", field = "Lnet/minecraft/entity/player/PlayerInventory;main:[Lnet/minecraft/item/ItemStack;")
    @Definition(id = "selectedSlot", field = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I")
    @Definition(id = "clone", method = "Lnet/minecraft/item/ItemStack;clone(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;")
    @Expression("this.player.inventory.main[this.player.inventory.selectedSlot] = clone(this.player.inventory.main[this.player.inventory.selectedSlot])")
    @WrapOperation(method="onPlayerInteractBlock", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void test3(ItemStack[] array, int index, ItemStack value, Operation<Void> original,
             @Share(value="isNotItemWithDuration", namespace = "farn_util") LocalBooleanRef ref) {
        if(ref.get()) {
            original.call(array, index, value);
        }
    }

    @WrapWithCondition(method="onPlayerInteractBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/ScreenHandler;sendContentUpdates()V"))
    public boolean test4(ScreenHandler instance,
             @Share(value="isNotItemWithDuration", namespace = "farn_util") LocalBooleanRef ref) {
        return ref.get();
    }

    @WrapWithCondition(method="onPlayerInteractBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", ordinal = 2))
    public boolean test5(ServerPlayNetworkHandler instance, Packet packet,
             @Share(value="isNotItemWithDuration", namespace = "farn_util") LocalBooleanRef ref) {
        return ref.get();
    }

    @WrapOperation(method="onPlayerInteractBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/ScreenHandler;getSlot(Lnet/minecraft/inventory/Inventory;I)Lnet/minecraft/screen/slot/Slot;"))
    public Slot test6(ScreenHandler instance, Inventory index, int i, Operation<Slot> original,
                      @Share(value="isNotItemWithDuration", namespace = "farn_util") LocalBooleanRef ref) {
        if(ref.get()) {
            return null;
        }
        return original.call(instance, index, i);
    }
}
