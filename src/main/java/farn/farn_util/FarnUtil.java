package farn.farn_util;

import farn.farn_util.impl.CustomPaticleImpl;
import net.minecraft.client.particle.Particle;

public class FarnUtil {
    private static boolean staticItemRender = false;

    @SuppressWarnings("unused")
    public static void setStaticItemRender(boolean bool) {
        staticItemRender = bool;
    }

    @SuppressWarnings("unused")
    public static boolean isStaticItemRender() {
        return staticItemRender;
    }

    @SuppressWarnings("unused")
    public static void addParticle(Particle particle) {
        if (CustomPaticleImpl.CUSTOM_PARTICLES.size() >= 4000) {
            CustomPaticleImpl.CUSTOM_PARTICLES.remove(0);
        }
        CustomPaticleImpl.CUSTOM_PARTICLES.add(particle);
    }
}
