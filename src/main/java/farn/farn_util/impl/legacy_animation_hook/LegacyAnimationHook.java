package farn.farn_util.impl.legacy_animation_hook;

import farn.farn_util.api.animation_hook.bipedmodel.BipedModelEvent;
import farn.farn_util.api.animation_hook.player_render.FirstPersonItemRotationEvent;
import farn.farn_util.api.animation_hook.player_render.PlayerRenderEvent;
import farn.farn_util.api.biped_model_extended.BipedModelHandler;
import farn.farn_util.api.item_usage.ActionHandler;
import net.mine_diver.unsafeevents.listener.EventListener;

@SuppressWarnings({"deprecrated","unused"})
public class LegacyAnimationHook {

    @EventListener
    public void setBipedModelAngle(BipedModelEvent.SetAngle event) {
        BipedModelHandler.setAngles.iterate(
                event.model,
                event.limbAngle,
                event.limbDistance,
                event.animationProgress,
                event.headYaw,
                event.headPitch,
                event.scale
        );
    }

    @EventListener
    public void renderBipedModel(BipedModelEvent.Render event) {
        BipedModelHandler.renderModel.iterate(
                event.model,
                event.limbAngle,
                event.limbDistance,
                event.animationProgress,
                event.headYaw,
                event.headPitch,
                event.scale
        );
    }

    @EventListener
    public void firstPersonItemRotation(FirstPersonItemRotationEvent event) {
        if(event.plr.farnutil_isUsingItem()) {
            ActionHandler iden = event.plr.farnutil_getActionType(event.heldStack);
            if(iden != null && iden.animation != null)
                iden.animation.applyFirstPersonItemRotation(
                        event.tick,
                        event.avgHeight,
                        event.plr,
                        event.heldStack
                );
        }
    }

    @EventListener
    public void thirdPersonItemRotation(FirstPersonItemRotationEvent event) {
        if(event.plr.farnutil_isUsingItem()) {
            ActionHandler action = event.plr.farnutil_getActionType(event.heldStack);
            if(action != null && action.animation != null)
                action.animation.applyThirdPersonItemRotation(
                        event.tick,
                        event.plr,
                        event.heldStack
                );
        }
    }

    @EventListener
    public void beforePlayerRender(PlayerRenderEvent.Before event) {
        if(event.player.farnutil_isUsingItem()) {
            ActionHandler action = event.player.farnutil_getActionType(event.heldStack);
            if(action != null && action.animation != null) {
                action.animation.beforePlayerRender(
                        event.renderer,
                        event.player,
                        event.heldStack,
                        event.x,
                        event.y,
                        event.z,
                        event.yaw,
                        event.pitch
                );
            }
        }
    }

    @EventListener
    public void beforePlayerRender(PlayerRenderEvent.After event) {
        if(event.player.farnutil_isUsingItem()) {
            ActionHandler action = event.player.farnutil_getActionType(event.heldStack);
            if(action != null && action.animation != null) {
                action.animation.afterPlayerRender(
                        event.renderer,
                        event.player,
                        event.heldStack,
                        event.x,
                        event.y,
                        event.z,
                        event.yaw,
                        event.pitch
                );
            }
        }
    }
}
