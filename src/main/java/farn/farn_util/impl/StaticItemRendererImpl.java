package farn.farn_util.impl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import farn.farn_util.FarnUtil;
import net.modificationstation.stationapi.api.client.render.model.BakedModel;
import net.modificationstation.stationapi.api.client.render.model.json.ModelTransformation;

public class StaticItemRendererImpl {

    public static void changeRotationArsenic(float angle, float x, float y, float z, Operation<Void> original) {
        if(!FarnUtil.isStaticItemRender()) original.call(angle,x,y,z);
    }

    public static void changeRotation3D(float angle, float x, float y, float z, Operation<Void> original) {
        if(FarnUtil.isStaticItemRender()) original.call(180.0F, 0.0F,1.0F,0.0F);
        else original.call(angle,x,y,z);
    }

    public static void undoBopping(float x, float y, float z, Operation<Void> original, LocalFloatRef offset) {
        original.call(x, FarnUtil.isStaticItemRender() ? y - offset.get() : y, z);
    }

    public static void genericStopTranslate(float x, float y, float z, Operation<Void> original) {
        if(!FarnUtil.isStaticItemRender()) original.call(x,y,z);;
    }

    public static void resizeItem(float x, float y, float z, Operation<Void> original) {
        if(FarnUtil.isStaticItemRender())
            original.call(x + 0.0128205F, y + 0.0128205F, z + 0.0128205F);
        else
            original.call(x,y,z);
    }

    public static void undoBoppingJson(float x, float y, float z, Operation<Void> original, float ogY, BakedModel model) {
        original.call(x, FarnUtil.isStaticItemRender() ? ogY * model.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY() : y, z);
    }
}
