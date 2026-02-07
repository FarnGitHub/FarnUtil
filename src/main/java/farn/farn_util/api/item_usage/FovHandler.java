package farn.farn_util.api.item_usage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;


public class FovHandler {
    private static float modernbow_fovMultiplier;
    private static float modernbow_fovPrev;

    public static float getFov(PlayerEntity player) {
        return player.UseApi_hasUsingItem() ? getFovRaw() : 1.0F;
    }

    private static float getFovRaw() {
        modernbow_fovPrev = modernbow_fovMultiplier;
        modernbow_fovMultiplier += (getFovMultiFromItem() - modernbow_fovMultiplier) * 0.5F;
        if(modernbow_fovMultiplier > 1.5F) {
            modernbow_fovMultiplier = 1.5F;
        }

        if(modernbow_fovMultiplier < 0.1F) {
            modernbow_fovMultiplier = 0.1F;
        }

        return modernbow_fovPrev + (modernbow_fovMultiplier - modernbow_fovPrev);
    }

    private static float getFovMultiFromItem() {
        return Minecraft.INSTANCE.player != null ? Minecraft.INSTANCE.player.UseApi_getFovMultiplier() : 1.0F;
    }
}
