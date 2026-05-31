package farn.farn_util.impl.item_usage;

import farn.farn_util.FarnUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;

public class ItemUsageImplServer {

    public static void updateAction(boolean hasAction, Entity ent) {
        if(FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) return;
        MessagePacket packet = new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_actionUpdated"));
        packet.booleans = new boolean[]{hasAction};
        packet.ints = new int[]{ent.id};
        PacketHelper.sendToAllTracking(ent, packet);
    }
}
