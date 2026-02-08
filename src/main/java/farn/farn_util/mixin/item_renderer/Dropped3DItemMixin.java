package farn.farn_util.mixin.item_renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import farn.farn_util.impl.static_item.StaticItemRendererImpl;
import farn.threeD_item.Item3D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item3D.class)
public class Dropped3DItemMixin {

    //Same as ArsenicItemRendererMixin but 3D, apply when there is 3D Dropped item mod
    @WrapOperation(method="render3DVanilla", at = {@At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1), @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0)})
    private static void disableRotation(float angle, float x, float y, float z, Operation<Void> original) {
        StaticItemRendererImpl.changeRotation3D(angle, x, y, z, original);
    }

    @WrapOperation(method="render3DVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    private static void preventItemBopping(float x, float y, float z, Operation<Void> original, @Local(ordinal = 4, argsOnly = true) LocalFloatRef offset) {
        StaticItemRendererImpl.undoBopping(x,y,z,original,offset);
    }

    @WrapOperation(method="render3DVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 1))
    private static void stopRandomOffset(float x, float y, float z, Operation<Void> original) {
        StaticItemRendererImpl.genericStopTranslate(x,y,z,original);
    }

    @WrapOperation(method="render3DVanilla", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glScalef(FFF)V", ordinal = 1))
    private static void renderInFrame1(float x, float y, float z, Operation<Void> original) {
        StaticItemRendererImpl.resizeItem(x,y,z,original);
    }
}
