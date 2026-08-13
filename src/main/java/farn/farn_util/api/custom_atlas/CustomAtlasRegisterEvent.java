package farn.farn_util.api.custom_atlas;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.util.Identifier;

@Environment(EnvType.CLIENT)
public class CustomAtlasRegisterEvent extends Event {

    public CustomAtlas registerBlockAtlas(String texturePath, Identifier baseId) {
        return new CustomAtlas(texturePath, baseId, false);
    }

    public CustomAtlas registerItemAtlas(String texturePath, Identifier baseId) {
        return new CustomAtlas(texturePath, baseId, true);
    }
}
