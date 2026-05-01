package farn.farn_util.api.item_usage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Handle fov for item action api
 */
public class FovHandler {
    private static float fovCurrent;

    public static float getFov() {
        float fovPrev = fovCurrent;
        fovCurrent += (getItemFov() - fovCurrent) * 0.5F;
        if(fovCurrent > 1.5F) {
            fovCurrent = 1.5F;
        } else if(fovCurrent < 0.1F) {
            fovCurrent = 0.1F;
        }

        return fovPrev + (fovCurrent - fovPrev);
    }

    private static float getItemFov() {
        PlayerEntity plr = Minecraft.INSTANCE.player;
        return plr != null ? plr.farnutil_getFovMultiplier() : 1.0F;
    }
}
