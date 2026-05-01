package farn.farn_util.api.animation_hook.bipedmodel;

import net.mine_diver.unsafeevents.Event;
import net.minecraft.client.render.entity.model.BipedEntityModel;

/**
 * Call for entity model and animation staff
 * make sure to use BipedModelEvent.SetAngle and BipedModelEvent.Render
 * BipedModelEvent on it own does nothing
 */
public abstract class BipedModelEvent extends Event {
    public BipedEntityModel model;
    public float limbAngle;
    public float limbDistance;
    public float animationProgress;
    public float headYaw;
    public float headPitch;
    public float scale;

    public void setVar(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        this.model = model;
        this.limbAngle = limbAngle;
        this.limbDistance = limbDistance;
        this.animationProgress = animationProgress;
        this.headYaw = headYaw;
        this.headPitch = headPitch;
        this.scale = scale;
    }

    public static final class SetAngle extends BipedModelEvent {}

    public static final class Render extends BipedModelEvent {}
}
