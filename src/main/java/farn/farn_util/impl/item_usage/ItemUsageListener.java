package farn.farn_util.impl.item_usage;

import farn.farn_util.FarnUtil;
import farn.farn_util.impl.id_tracker.network.EntityIDTrackerUpdatePacket;
import farn.farn_util.impl.id_tracker.network.LivingEntitySpawnPacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.util.SideUtil;

public class ItemUsageListener {

    @EventListener
    public void registerMessagePacket(MessageListenerRegistryEvent event) {
        event.register(FarnUtil.NAMESPACE.id("item_usage_api_stop"),
                (plr, p) ->
                        pickSide(false, plr::farnutil_stopUsingItem)
        );
        event.register(FarnUtil.NAMESPACE.id("item_usage_api_finished"),
                (plr, p) ->
                        pickSide(true, plr::farnutil_finishUsingItem)
        );
    }

    @EventListener
    public void registerPacket(PacketRegisterEvent event) {
        event.register(FarnUtil.NAMESPACE.id("entity_tracker_update"), EntityIDTrackerUpdatePacket.TYPE);
        event.register(FarnUtil.NAMESPACE.id("living_spawn"), LivingEntitySpawnPacket.TYPE);
    }

    private void pickSide(boolean client, Runnable run) {
        if(client)
            SideUtil.run(run, ()->{});
        else
            SideUtil.run(()->{}, run);
    }
}
