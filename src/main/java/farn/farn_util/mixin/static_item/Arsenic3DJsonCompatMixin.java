package farn.farn_util.mixin.static_item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.farn_util.api.static_item.StaticItemRendererAPI;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ArsenicItemRenderer.class, priority = 1100)
public class Arsenic3DJsonCompatMixin {

    @WrapOperation(method="renderModel", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1))
    void turnOffRotationJson(float angle, float x, float y, float z, Operation<Void> original) {
         if(!StaticItemRendererAPI.isStaticItemRender())
             original.call(angle,x,y,z);
    }
}
