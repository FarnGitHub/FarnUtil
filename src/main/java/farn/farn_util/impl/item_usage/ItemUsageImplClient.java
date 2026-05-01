package farn.farn_util.impl.item_usage;

import farn.farn_util.FarnUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.ClientWorld;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;

public class ItemUsageImplClient {

    @Environment(EnvType.CLIENT)
    public static void stopUsing(PlayerEntity player) {
        if(Minecraft.INSTANCE.interactionManager instanceof MultiplayerInteractionManager mp) {
            mp.updateSelectedSlot();
            PacketHelper.send(new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_stop")));
        }
        player.farnutil_stopUsingItem();
    }

    @Environment(EnvType.CLIENT)
    public static void handleSetActionClient(PlayerEntity plr, MessagePacket msg) {
        if(plr == null) return;
        if(plr.world instanceof ClientWorld clientWorld)
            if(clientWorld.getEntity(msg.ints[0]) instanceof PlayerEntity target)
                target.farnutil_setHasActionOnly(msg.booleans[0]);
    }
}
