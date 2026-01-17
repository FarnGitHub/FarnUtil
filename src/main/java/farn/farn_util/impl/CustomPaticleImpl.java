package farn.farn_util.impl;

import farn.farn_util.api.ParticleDisableQuadDraw;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;

import java.util.ArrayList;
import java.util.List;

public class CustomPaticleImpl {
    public static final List<Particle> CUSTOM_PARTICLES = new ArrayList<>();

    public static void renderMultipleParticles(float partialTicks, float var3, float var4, float var5, float var6, float var7) {
        Tessellator tessellator = Tessellator.INSTANCE;

        for(Particle particle : CUSTOM_PARTICLES){
            if(!(particle instanceof ParticleDisableQuadDraw)) tessellator.startQuads();
            particle.render(tessellator, partialTicks, var3, var7, var4, var5, var6);
            if(!(particle instanceof ParticleDisableQuadDraw)) tessellator.draw();
        }
    }

    public static void clearParticles() {
        CUSTOM_PARTICLES.clear();
    }

    public static void clearDeadParticles() {
        for(int i = 0; i < CUSTOM_PARTICLES.size(); ++i) {
            Particle particle = CUSTOM_PARTICLES.get(i);
            particle.tick();
            if (particle.dead) {
                CUSTOM_PARTICLES.remove(i--);
            }
        }
    }
}
