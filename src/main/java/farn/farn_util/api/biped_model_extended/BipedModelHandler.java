package farn.farn_util.api.biped_model_extended;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public class BipedModelHandler {
    private static final ObjectArrayList<BipedModelConsumer> setAngles = new ObjectArrayList<>();
    private static final ObjectArrayList<BipedModelConsumer> renderModel = new ObjectArrayList<>();

    @SuppressWarnings("unused")
    public static void registerBipedModelSetAngle(BipedModelConsumer biped) {
        setAngles.add(biped);
    }

    @SuppressWarnings("unused")
    public static void registerBipedModelRender(BipedModelConsumer biped) {
        renderModel.add(biped);
    }

    public static void loopRender(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        if(renderModel.isEmpty()) return;
        for(BipedModelConsumer consumer : renderModel)
            consumer.accept(model,
                    limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
    }

    public static void loopAngles(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        if(setAngles.isEmpty()) return;
        for(BipedModelConsumer consumer : setAngles)
            consumer.accept(model,
                    limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
    }

    public interface BipedModelConsumer {
        void accept(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale);
    }
}
