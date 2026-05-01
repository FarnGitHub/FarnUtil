package farn.farn_util.api.animation_hook.player_render;

import net.mine_diver.unsafeevents.Event;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Call when during super.render(playerEntity, d, var11, f, g, h); inside PlayerEntityRendererClass
 * make sure to use PlayerRenderEvent.Before and PlayerRenderEvent.After
 * PlayerRenderEvent on it own does nothing
 */
public abstract class PlayerRenderEvent extends Event {
    public PlayerEntityRenderer renderer;
    public PlayerEntity player;
    public ItemStack heldStack;
    public double x, y, z;
    public float yaw, pitch;

    public void setVar(PlayerEntityRenderer renderer, PlayerEntity player, ItemStack heldStack, double x, double y, double z, float yaw, float pitch) {
        this.renderer = renderer;
        this.player = player;
        this.heldStack = heldStack;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static final class Before extends PlayerRenderEvent {
    }

    public static final class After extends PlayerRenderEvent {
    }
}
