package farn.farn_util.api.biped_model_extended;

import net.minecraft.client.render.entity.model.BipedEntityModel;

public class BipedModelHandler {
    public static final ModelEvents setAngles = new ModelEvents();
    public static final ModelEvents renderModel = new ModelEvents();

    @Deprecated
    public static void registerBipedModelSetAngle(BipedModelConsumer biped) {
        setAngles.register(biped);
    }

    @Deprecated
    public static void registerBipedModelRender(BipedModelConsumer biped) {
        renderModel.register(biped);
    }

    @Deprecated
    public interface BipedModelConsumer extends ModelEvents.Event {
        void accept(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale);

        @Deprecated
        default void call(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
            this.accept(model, limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        }
    }
}
