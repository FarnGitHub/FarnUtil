package farn.farn_util.api.particle;

/**
 * Implement this on your Particle class
 * This prevent tessellator.startQuads(); and tessellator.draw();
 * from being called inside FarnUtil's Custom Particle's Render
 */
public interface ParticleDisableQuadDraw {
}
