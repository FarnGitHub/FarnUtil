package farn.farn_util.mixin.item_renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import farn.farn_util.impl.StaticItemRendererImpl;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArsenicItemRenderer.class)
public class ArsenicItemRendererMixin {

    //turn off item rotation when rendering campfire's items
    @WrapOperation(method="renderVanilla", at = {@At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1), @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0)})
    void turnOffRotation(float angle, float x, float y, float z, Operation<Void> original) {
        StaticItemRendererImpl.changeRotationArsenic(angle,x,y,z, original);
    }

    @WrapOperation(method="renderVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    void preventItemBopping(float x, float y, float z, Operation<Void> original, @Local(ordinal = 4, argsOnly = true) LocalFloatRef offset) {
        StaticItemRendererImpl.undoBopping(x,y,z,original,offset);
    }

    @WrapOperation(method="renderVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 1))
    void stopRandomOffset(float x, float y, float z, Operation<Void> original) {
        StaticItemRendererImpl.genericStopTranslate(x,y,z,original);
    }

    @WrapOperation(method="renderVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glScalef(FFF)V", ordinal = 1))
    void renderInFrame1(float x, float y, float z, Operation<Void> original) {
        StaticItemRendererImpl.resizeItem(x,y,z,original);
    }
}
