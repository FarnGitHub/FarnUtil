package farn.farn_util.api.static_item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

//Disable Rotation and translate on ItemRenderer.class, purely clientside
//Useful for render something like item on campfire
@Environment(EnvType.CLIENT)
public class StaticItemRendererAPI {

    private static boolean staticItemRender = false;

    @SuppressWarnings("unused")
    public static void setStaticItemRender(boolean bool) {
        staticItemRender = true;
    }

    @SuppressWarnings("unused")
    public static boolean isStaticItemRender() {
        return staticItemRender;
    }
}
