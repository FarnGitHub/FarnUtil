package farn.farn_util.mixin.item_renderer;

import farn.farn_util.api.static_item.StaticItemRendererAPI;
import farn.threeD_item.Item3D;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.render.model.BakedModel;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ArsenicItemRenderer.class, priority = 1100)
public class Arsenic3DJsonCompatMixin {

    @Inject(method="renderModel", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1))
    void turnOffRotationJson(ItemEntity item, float x, float y, float z, float delta, ItemStack var10, float var11, float var12, byte renderedAmount, SpriteAtlasTexture atlas, BakedModel model, CallbackInfo ci) {
         Item3D.rotateJsonItem = !StaticItemRendererAPI.isStaticItemRender();
    }
}
