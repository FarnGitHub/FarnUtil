package farn.farn_util;

import farn.farn_util.api.particle.ParticleAPI;
import farn.farn_util.api.static_item.StaticItemRendererAPI;
import farn.farn_util.impl.item_usage.ItemUsageImplClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.particle.Particle;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.util.SideUtil;
import org.apache.logging.log4j.Logger;


public class FarnUtil {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    @EventListener
    public void registerPacket(MessageListenerRegistryEvent event) {
        event.register(NAMESPACE.id("item_usage_api_stop"), (plr, messagePacket) -> {
            SideUtil.run(() -> {}, plr::farnutil_stopUsingItem);
        });
        event.register(NAMESPACE.id("item_usage_api_finished"), (plr, messagePacket) -> {
            SideUtil.run(plr::farnutil_finishUsingItem, () -> {});
        });
        event.register(NAMESPACE.id("item_usage_api_actionUpdated"), (plr, messagePacket) -> {
            SideUtil.run(() -> ItemUsageImplClient.handleSetActionClient(plr, messagePacket)
                    ,() -> {});
        });
    }
}
