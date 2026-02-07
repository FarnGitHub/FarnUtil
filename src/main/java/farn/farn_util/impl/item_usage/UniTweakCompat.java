package farn.farn_util.impl.item_usage;

import farn.farn_util.api.item_usage.FovHandler;
import net.danygames2014.unitweaks.event.RegisterUniTweaksCompatEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;

public class UniTweakCompat {

    @EventListener
    public void registerFov(RegisterUniTweaksCompatEvent event) {
        event.registerFovCompat((fov, tick) -> fov * getFovMulti());
    }

    private float getFovMulti() {
        return Minecraft.INSTANCE.player != null ? FovHandler.getFov(Minecraft.INSTANCE.player) : 1.0F;
    }
}
