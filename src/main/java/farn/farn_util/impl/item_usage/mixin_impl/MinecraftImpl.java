package farn.farn_util.impl.item_usage.mixin_impl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.farn_util.impl.item_usage.ItemUsageImplClient;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class MinecraftImpl {
    private static boolean noItemUsing = true;

    public static void handleStopUsingItem() {
        if(Minecraft.INSTANCE.player.farnutil_isUsingItem()) {
            if (!Mouse.isButtonDown(1)) {
                ItemUsageImplClient.stopUsingItemClient(Minecraft.INSTANCE.player);
            }
            noItemUsing = false;
        } else
            noItemUsing = true;
    }

    public static boolean makeSureItemIsNotOnUse(Operation<Boolean> original) {
        return noItemUsing && original.call();
    }

    public static boolean isPlayerNotUsingItemAndOriginalCall(Minecraft instance, Operation<Boolean> original) {
        return !instance.player.farnutil_isUsingItem() && original.call(instance);
    }
}
