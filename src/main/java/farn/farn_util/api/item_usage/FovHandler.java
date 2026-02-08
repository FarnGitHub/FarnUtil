package farn.farn_util.api.item_usage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

//Handle fov when using item
public class FovHandler {
    private static float fovCurrent;

    public static float getFovWithCheck(PlayerEntity player) {
        return player.farnutil_isUsingItem() ? getFov() : 1.0F;
    }

    public static float getFov() {
        float fovPrev = fovCurrent;
        fovCurrent += (getItemFov() - fovCurrent) * 0.5F;
        if(fovCurrent > 1.5F) {
            fovCurrent = 1.5F;
        }

        if(fovCurrent < 0.1F) {
            fovCurrent = 0.1F;
        }

        return fovPrev + (fovCurrent - fovPrev);
    }

    private static float getItemFov() {
        return Minecraft.INSTANCE.player != null ? Minecraft.INSTANCE.player.farnutil_getFovMultiplier() : 1.0F;
    }
}
