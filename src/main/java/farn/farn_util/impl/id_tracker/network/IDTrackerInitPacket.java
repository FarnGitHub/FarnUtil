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

public class IDTrackerInitPacket extends Packet implements ManagedPacket<IDTrackerInitPacket> {
    public int entityId;
    public IDDataTracker idDataTracker;
    public List<IDDataTrackerEntry<?>> idTrackedValues;

    public static final PacketType<IDTrackerInitPacket> TYPE = PacketType.builder(true, false, IDTrackerInitPacket::new).build();

    public IDTrackerInitPacket() {
    }

    public IDTrackerInitPacket(Entity entity) {
        this.entityId = entity.id;
        this.idDataTracker = entity.farnutil_getIdDataTracker();
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.idTrackedValues = IDDataTracker.readEntries(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
            this.idDataTracker.writeAllEntries(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        SideUtil.runClient(this::applyClient);
    }

    @Environment(EnvType.CLIENT)
    public void applyClient() {
        ClientWorld clientWorld = ((ClientWorld)PlayerHelper.getPlayerFromGame().world);
        Entity entity = clientWorld.getEntity(this.entityId);
        if(entity != null) {
            this.idDataTracker = entity.farnutil_getIdDataTracker();
            this.idDataTracker.writeUpdatedEntries(idTrackedValues);
        }
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public @NotNull PacketType<IDTrackerInitPacket> getType() {
        return TYPE;
    }
}
