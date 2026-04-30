package farn.farn_util.impl.game_hook;

import farn.farn_util.api.game_hook.RenderWorldLastEvent;
import net.minecraft.client.render.WorldRenderer;
import net.modificationstation.stationapi.api.StationAPI;
import org.lwjgl.opengl.GL11;

public class RenderWorldLastImpl {

    public static void pushMatrixCloud() {
        GL11.glPushMatrix();
    }

    public static void popMatrixCloud(WorldRenderer worldRenderer, float tick) {
        GL11.glPopMatrix();
        StationAPI.EVENT_BUS.post(new RenderWorldLastEvent(worldRenderer, tick));
    }
}
