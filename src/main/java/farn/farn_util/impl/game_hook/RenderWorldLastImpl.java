package farn.farn_util.impl.game_hook;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.game_hook.RenderWorldLastEvent;
import net.minecraft.client.render.WorldRenderer;
import org.lwjgl.opengl.GL11;

public class RenderWorldLastImpl {

    public static void pushMatrixCloud() {
        GL11.glPushMatrix();
    }

    public static void popMatrixCloud(WorldRenderer worldRenderer, float tick) {
        GL11.glPopMatrix();
        FarnUtil.setupEvent(new RenderWorldLastEvent(worldRenderer, tick));
    }
}
