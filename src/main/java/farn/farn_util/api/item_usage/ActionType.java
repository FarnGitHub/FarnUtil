package farn.farn_util.api.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class ActionType {

    public final Identifier id;

    @Environment(EnvType.CLIENT)
    protected ActionTypeAnimation animation;

    public ActionType(Identifier id) {
        this.id = id;
    }


    @Environment(EnvType.CLIENT)
    public ActionTypeAnimation getAnimation() {
        return animation;
    }

}
