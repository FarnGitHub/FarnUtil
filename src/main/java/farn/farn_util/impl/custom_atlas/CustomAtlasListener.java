package farn.farn_util.impl.custom_atlas;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.custom_atlas.CustomAtlas;
import farn.farn_util.api.custom_atlas.CustomAtlasRegisterEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

@Environment(EnvType.CLIENT)
public class CustomAtlasListener {

    @EventListener
    public void reloadTexture(TextureRegisterEvent event) {
        CustomAtlas.reloads();
    }

    @EventListener
    public void initEvent(InitEvent event) {
        FarnUtil.setupEvent(new CustomAtlasRegisterEvent());
    }
}
