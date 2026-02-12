package farn.farn_util.api.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class ActionHandler {

    public final Identifier id;

    @Environment(EnvType.CLIENT)
    public final ActionAnimator animation;

    public ActionHandler(Identifier id) {
        this.id = id;
        animation = createAnimation();
    }

    @Environment(EnvType.CLIENT)
    protected ActionAnimator createAnimation() {
        return new ActionAnimator();
    }

}
