package farn.farn_util.api.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class ActionHandler {

    public final Identifier id;

    @Environment(EnvType.CLIENT)
    public ActionAnimator animation;

    public ActionHandler(Identifier id) {
        this.id = id;
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            animation = createAnimation();
    }

    @Environment(EnvType.CLIENT)
    protected ActionAnimator createAnimation() {
        return new ActionAnimator();
    }

}
