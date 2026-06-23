package farn.farn_util.impl.id_tracker.network;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.LivingEntitySpawnS2CPacket;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class LivingEntitySpawnPacket extends LivingEntitySpawnS2CPacket implements ManagedPacket<LivingEntitySpawnPacket> {
    public IDDataTracker idDataTracker;
    public List<IDDataTrackerEntry> idTrackedValues;

    public static final PacketType<LivingEntitySpawnPacket> TYPE = PacketType.builder(true, false, LivingEntitySpawnPacket::new).build();

    public LivingEntitySpawnPacket() {
        super();
    }

    public LivingEntitySpawnPacket(LivingEntity livingEntity) {
        super(livingEntity);
        this.idDataTracker = livingEntity.farnutil_getIdDataTracker();
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            super.read(stream);
            this.idTrackedValues = IDDataTracker.readEntries(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            super.write(stream);
            this.idDataTracker.writeAllEntries(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull PacketType<LivingEntitySpawnPacket> getType() {
        return TYPE;
    }
}
