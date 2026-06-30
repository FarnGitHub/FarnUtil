package farn.farn_util;

import farn.farn_util.impl.id_tracker.network.EntityIDTrackerUpdatePacket;
import farn.farn_util.impl.id_tracker.network.LivingEntitySpawnPacket;
import farn.farn_util.impl.item_usage.ItemUsageImplClient;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.Event;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.util.SideUtil;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class FarnUtil {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    @EventListener
    public void registerMessagePacket(MessageListenerRegistryEvent event) {
        event.register(NAMESPACE.id("item_usage_api_stop"),
                (plr, p) ->
                pickSide(false, plr::farnutil_stopUsingItem)
        );
        event.register(NAMESPACE.id("item_usage_api_finished"),
                (plr, p) ->
                pickSide(true, plr::farnutil_finishUsingItem)
        );
    }

    @EventListener
    public void registerPacket(PacketRegisterEvent event) {
        event.register(NAMESPACE.id("entity_tracker_update"), EntityIDTrackerUpdatePacket.TYPE);
        event.register(NAMESPACE.id("living_spawn"), LivingEntitySpawnPacket.TYPE);
    }

    @EventListener
    public void farnutilInit(InitEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("farn_util:init", Object.class).forEach(EntrypointManager::setup);
    }

    private void pickSide(boolean client, Runnable run) {
        if(client)
            SideUtil.run(run, ()->{});
        else
            SideUtil.run(()->{}, run);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T extends Event> T setupEvent(T t) {
        return StationAPI.EVENT_BUS.post(t);
    }
}
