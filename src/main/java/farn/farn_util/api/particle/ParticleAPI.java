package farn.farn_util.api.particle;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;


/**
 * Use to add particle, purely clientside
 * Move to be last thing to render to have better transparency support
 */
@Environment(EnvType.CLIENT)
public class ParticleAPI {
    public static final ObjectArrayList<Particle> CUSTOM_PARTICLES = new ObjectArrayList<>();

    @SuppressWarnings("unused")
    public static void addParticle(Particle particle) {
        if (CUSTOM_PARTICLES.size() >= 4000) {
            CUSTOM_PARTICLES.remove(0);
        }
        CUSTOM_PARTICLES.add(particle);
    }
}
