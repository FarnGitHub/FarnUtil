package farn.farn_util.api.custom_atlas;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.util.Namespace;

@Environment(EnvType.CLIENT)
public class CustomAtlasRegisterEvent extends Event {

    public CustomAtlas ofBlock(Namespace namespace, String texturePath) {
        return of(namespace, texturePath, false);
    }

    public CustomAtlas ofItem(Namespace namespace, String texturePath) {
        return of(namespace, texturePath, true);
    }

    private CustomAtlas of(Namespace namespace, String texturePath, boolean itemAtlas) {
        return CustomAtlas.of(namespace, texturePath, itemAtlas);
    }
}
