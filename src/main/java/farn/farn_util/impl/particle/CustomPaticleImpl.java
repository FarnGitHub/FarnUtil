package farn.farn_util.impl.particle;

import farn.farn_util.api.particle.ParticleDisableQuadDraw;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;

import farn.farn_util.api.particle.ParticleAPI;

public class CustomPaticleImpl {

    public static void renderMultipleParticles(float partialTicks, float var3, float var4, float var5, float var6, float var7) {
        Tessellator tessellator = Tessellator.INSTANCE;
        for(Particle particle : ParticleAPI.CUSTOM_PARTICLES){
            if(!(particle instanceof ParticleDisableQuadDraw)) tessellator.startQuads();
            particle.render(tessellator, partialTicks, var3, var7, var4, var5, var6);
            if(!(particle instanceof ParticleDisableQuadDraw)) tessellator.draw();
        }
    }

    public static void clearParticles() {
        ParticleAPI.CUSTOM_PARTICLES.clear();
    }

    public static void clearDeadParticles() {
        for(int i = 0; i < ParticleAPI.CUSTOM_PARTICLES.size(); ++i) {
            Particle particle = ParticleAPI.CUSTOM_PARTICLES.get(i);
            particle.tick();
            if (particle.dead) {
                ParticleAPI.CUSTOM_PARTICLES.remove(i--);
            }
        }
    }
}
