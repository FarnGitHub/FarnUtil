package farn.farn_util.impl.id_tracker.network;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.ClientWorld;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.util.SideUtil;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class EntityIDTrackerUpdatePacket extends Packet implements ManagedPacket<EntityIDTrackerUpdatePacket> {
    public int id;
    private List<IDDataTrackerEntry> trackedValues;
    public static final PacketType<EntityIDTrackerUpdatePacket> TYPE = PacketType.builder(true, false, EntityIDTrackerUpdatePacket::new).build();

    public EntityIDTrackerUpdatePacket() {
        super();
    }

    @Environment(EnvType.SERVER)
    public EntityIDTrackerUpdatePacket(int entityId, IDDataTracker dataTracker) {
        super();
        this.id = entityId;
        this.trackedValues = dataTracker.getDirtyEntries();
    }

    @Override
    public void read(DataInputStream stream) {

    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.id);
            IDDataTracker.writeEntries(this.trackedValues, stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        SideUtil.run(this::applyClient, () -> {});

    }

    @Environment(EnvType.CLIENT)
    public void applyClient() {
        Entity entity = ((ClientWorld) PlayerHelper.getPlayerFromGame().world).getEntity(id);
        if (entity != null && trackedValues != null) {
            entity.farnutil_getIdDataTracker().writeUpdatedEntries(trackedValues);
        }
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public @NotNull PacketType<EntityIDTrackerUpdatePacket> getType() {
        return TYPE;
    }
}
