package farn.farn_util.api.animation_hook.player_render;

import net.mine_diver.unsafeevents.Event;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.render.RendererAccess;
import net.modificationstation.stationapi.api.client.render.model.VanillaBakedModel;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.ArsenicRenderer;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicOverlayRenderer;

public class FirstPersonItemRotationEvent extends Event {
    public float tick;
    public float avgHeight;
    public ClientPlayerEntity plr;
    public  ItemStack heldStack;
    public HeldItemRenderer render;
    public ArsenicOverlayRenderer arsenicRender;
    public boolean vanillaModel;

    public FirstPersonItemRotationEvent(
            HeldItemRenderer render,
            ArsenicOverlayRenderer arsenicRender,
            float tick, float avgHeight,
            ClientPlayerEntity plr,
            ItemStack heldStack
    ) {
        this.render = render;
        this.arsenicRender = arsenicRender;
        this.tick = tick;
        this.avgHeight = avgHeight;
        this.plr = plr;
        this.heldStack = heldStack;
        this.vanillaModel = ArsenicRenderer.INSTANCE.
                bakedModelRenderer().getItemModels().getModel(this.heldStack)
                instanceof VanillaBakedModel;
    }
}
