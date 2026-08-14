package farn.farn_util.impl.item_usage;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.farn_util.FarnUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.ClientWorld;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.input.Mouse;

public class ItemUsageImplClient {

    @Environment(EnvType.CLIENT)
    public static void stopUsing(PlayerEntity player) {
        if(Minecraft.INSTANCE.interactionManager instanceof MultiplayerInteractionManager mp) {
            mp.updateSelectedSlot();
            PacketHelper.send(new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_stop")));
        }
        player.farnutil_stopUsingItem();
    }

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
        return Minecraft.INSTANCE.player.farnutil_isUsingItem();
    }
}
