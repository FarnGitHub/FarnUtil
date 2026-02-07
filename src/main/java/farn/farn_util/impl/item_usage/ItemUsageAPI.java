package farn.farn_util.impl.item_usage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.util.SideUtil;
import org.apache.logging.log4j.Logger;

public class ItemUsageAPI {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    @Environment(EnvType.CLIENT)
    public static void stopUsingItemClient(PlayerEntity player) {
        if(Minecraft.INSTANCE.interactionManager instanceof MultiplayerInteractionManager mp) {
            mp.updateSelectedSlot();
            PacketHelper.send(new MessagePacket(NAMESPACE.id("item_usage_api_stop")));
        }
        player.UseApi_stopUsingItem();
    }

    @EventListener
    public void registerPacket(MessageListenerRegistryEvent event) {
        event.register(NAMESPACE.id("item_usage_api_stop"), (plr, messagePacket) -> {
            SideUtil.run(() -> {}, plr::UseApi_stopUsingItem);
        });
        event.register(NAMESPACE.id("item_usage_api_finished"), (plr, messagePacket) -> {
            SideUtil.run(plr::UseApi_finishedUsingItem, () -> {});
        });
        event.register(NAMESPACE.id("item_usage_api_actionUpdated"), (plr, messagePacket) -> {
            plr.UseApi_setHasActionOnly(messagePacket.booleans[0]);
        });
    }
}
