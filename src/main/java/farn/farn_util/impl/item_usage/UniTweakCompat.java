package farn.farn_util.impl.item_usage;

import farn.farn_util.api.item_usage.FovHandler;
import net.danygames2014.unitweaks.event.RegisterUniTweaksCompatEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;

public class UniTweakCompat {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerFov(RegisterUniTweaksCompatEvent event) {
        event.registerFovCompat((fov, tick) -> fov * getFovMulti());
    }

    @Environment(EnvType.CLIENT)
    private float getFovMulti() {
        return Minecraft.INSTANCE.player != null ? FovHandler.getFovWithCheck(Minecraft.INSTANCE.player) : 1.0F;
    }
}
