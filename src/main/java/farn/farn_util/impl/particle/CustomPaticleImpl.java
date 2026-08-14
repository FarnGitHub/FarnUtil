package farn.farn_util.impl.particle;

import farn.farn_util.api.game_hook.RenderWorldLastEvent;
import farn.farn_util.api.particle.ParticleDisableQuadDraw;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;

import farn.farn_util.api.particle.ParticleAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CustomPaticleImpl {

    public static void render(float partialTicks, float var3, float var4, float var5, float var6, float var7) {
        Tessellator tessellator = Tessellator.INSTANCE;
        for(Particle particle : ParticleAPI.CUSTOM_PARTICLES){
            if(!(particle instanceof ParticleDisableQuadDraw)) tessellator.startQuads();
            particle.render(tessellator, partialTicks, var3, var7, var4, var5, var6);
            if(!(particle instanceof ParticleDisableQuadDraw)) tessellator.draw();
        }
    }

    public static void clear() {
        ParticleAPI.CUSTOM_PARTICLES.clear();
    }

    public static void tick() {
        for(int i = 0; i < ParticleAPI.CUSTOM_PARTICLES.size(); ++i) {
            Particle particle = ParticleAPI.CUSTOM_PARTICLES.get(i);
            particle.tick();
            if (particle.dead) {
                ParticleAPI.CUSTOM_PARTICLES.remove(i--);
            }
        }
    }

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
