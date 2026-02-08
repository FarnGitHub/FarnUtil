package farn.farn_util.api.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ActionTypeAnimation {

    @Environment(EnvType.CLIENT)
    public void applyFirstPersonItemRotation(float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack) {
    }

    @Environment(EnvType.CLIENT)
    public void applyThirdPersonItemRotation(float tick, PlayerEntity plr, ItemStack heldStack) {
    }
}
