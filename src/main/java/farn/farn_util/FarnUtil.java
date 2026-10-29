package farn.farn_util;

import farn.farn_util.api.dungeon.DungeonAPI;
import farn.farn_util.api.dungeon.DungeonLootHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.Event;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.init.InitFinishedEvent;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("unused")
public class FarnUtil {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    @EventListener
    public void farnutilInit(InitEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("farn_util:init", Object.class).forEach(EntrypointManager::setup);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T extends Event> T setupEvent(T t) {
        return StationAPI.EVENT_BUS.post(t);
    }
}
