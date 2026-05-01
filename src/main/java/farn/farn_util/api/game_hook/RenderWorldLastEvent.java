package farn.farn_util.api.game_hook;

import net.mine_diver.unsafeevents.Event;
import net.minecraft.client.render.WorldRenderer;

/**
 * Called after cloud render inside GameRenderer class
 */
public class RenderWorldLastEvent extends Event {
    public WorldRenderer renderer;
    public float tick;

    public RenderWorldLastEvent(WorldRenderer renderer, float tick) {
        this.renderer = renderer;
        this.tick = tick;
    }
}
