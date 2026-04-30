package farn.farn_util.api.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Deprecated
@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class ActionAnimator {

    public void applyFirstPersonItemRotation(float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack) {
    }

    public void applyThirdPersonItemRotation(float tick, PlayerEntity plr, ItemStack heldStack) {
    }

    public void beforePlayerRender(PlayerEntityRenderer renderer, PlayerEntity player, ItemStack heldStack, double x, double y, double z, float yaw, float pitch) {
    }

    public void afterPlayerRender(PlayerEntityRenderer renderer, PlayerEntity player, ItemStack heldStack, double x, double y, double z, float yaw, float pitch) {
    }

}
