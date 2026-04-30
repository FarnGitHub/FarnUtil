package farn.farn_util.api.animation_hook.player_render;

import net.mine_diver.unsafeevents.Event;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.render.model.VanillaBakedModel;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.ArsenicRenderer;

public class ThirdPersonItemRotationEvent extends Event {
    public float tick;
    public PlayerEntity plr;
    public ItemStack heldStack;
    public PlayerEntityRenderer renderer;
    public boolean vanillaModel;

    public ThirdPersonItemRotationEvent(
            PlayerEntityRenderer renderer,
            float tick,
            PlayerEntity plr,
            ItemStack heldStack
    ) {
        this.renderer = renderer;
        this.tick = tick;
        this.plr = plr;
        this.heldStack = heldStack;
        this.vanillaModel = ArsenicRenderer.INSTANCE.
                bakedModelRenderer().getItemModels().getModel(this.heldStack)
                instanceof VanillaBakedModel;
    }
}
