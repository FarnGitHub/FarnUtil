package farn.farn_util.impl.particle;

import farn.farn_util.api.game_hook.RenderWorldLastEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class ParticleRender {

    @EventListener
    public void renderParticle(RenderWorldLastEvent event) {
        LivingEntity camera = Minecraft.INSTANCE.camera;
        float var3 = MathHelper.cos(camera.yaw * (float)Math.PI / 180.0F);
        float var4 = MathHelper.sin(camera.yaw * (float)Math.PI / 180.0F);
        float var5 = -var4 * MathHelper.sin(camera.pitch * (float)Math.PI / 180.0F);
        float var6 = var3 * MathHelper.sin(camera.pitch * (float)Math.PI / 180.0F);
        float var7 = MathHelper.cos(camera.pitch * (float)Math.PI / 180.0F);
        Particle.xOffset = camera.lastTickX + (camera.x - camera.lastTickX) * (double)event.tick;
        Particle.yOffset = camera.lastTickY + (camera.y - camera.lastTickY) * (double)event.tick;
        Particle.zOffset = camera.lastTickZ + (camera.z - camera.lastTickZ) * (double)event.tick;
        CustomPaticleImpl.render(event.tick, var3, var4, var5, var6, var7);
    }
}
