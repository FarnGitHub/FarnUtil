package farn.farn_util.api.particle;

import net.minecraft.client.particle.Particle;

import java.util.ArrayList;
import java.util.List;

//Use to add particle, purely clientside
public class ParticleAPI {
    public static final List<Particle> CUSTOM_PARTICLES = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void addParticle(Particle particle) {
        if (CUSTOM_PARTICLES.size() >= 4000) {
            CUSTOM_PARTICLES.remove(0);
        }
        CUSTOM_PARTICLES.add(particle);
    }
}
