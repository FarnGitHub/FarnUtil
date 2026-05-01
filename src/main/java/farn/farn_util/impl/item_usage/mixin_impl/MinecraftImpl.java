package farn.farn_util.impl.item_usage.mixin_impl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.farn_util.impl.item_usage.ItemUsageImplClient;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class MinecraftImpl {
    public static final Minecraft mc = Minecraft.INSTANCE;

    public static void handleStopUsingItem() {
        if(isPlayerUsingItem() && !Mouse.isButtonDown(1))
            ItemUsageImplClient.stopUsing(Minecraft.INSTANCE.player);
    }

    public static boolean preventSingleUseWhenUsing(Operation<Boolean> original) {
        return !isPlayerUsingItem() && original.call();
    }

    public static boolean preventSingleUseWhenUsing2(Minecraft instance, Operation<Boolean> original) {
        return !isPlayerUsingItem() && original.call(instance);
    }

    private static boolean isPlayerUsingItem() {
        return mc.player.farnutil_isUsingItem();
    }
}
