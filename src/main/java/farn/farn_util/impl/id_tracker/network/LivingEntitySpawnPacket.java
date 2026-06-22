package farn.farn_util.impl.id_tracker.network;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.s2c.play.LivingEntitySpawnS2CPacket;
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

public class LivingEntitySpawnPacket extends LivingEntitySpawnS2CPacket implements ManagedPacket<LivingEntitySpawnPacket> {
    private IDDataTracker idDataTracker;
    private List<IDDataTrackerEntry> idTrackedValues;

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
    public void apply(NetworkHandler networkHandler) {
        SideUtil.run(this::applyClient, () -> {});
    }

    @Environment(EnvType.CLIENT)
    public void applyClient() {
        ClientWorld clientWorld = ((ClientWorld) PlayerHelper.getPlayerFromGame().world);
        double x = (double)this.x / 32.0;
        double y = (double)this.y / 32.0;
        double z = (double)this.z / 32.0;
        float yaw = (float)(this.yaw * 360) / 256.0F;
        float pitch = (float)(this.pitch * 360) / 256.0F;
        LivingEntity entity = (LivingEntity)EntityRegistry.create(this.entityType, clientWorld);
        entity.trackedPosX = this.x;
        entity.trackedPosY = this.y;
        entity.trackedPosZ = this.z;
        entity.id = this.id;
        entity.setPositionAndAngles(x, y, z, yaw, pitch);
        entity.interpolateOnly = true;
        clientWorld.forceEntity(this.id, entity);
        if (this.getTrackedValues() != null)
            entity.getDataTracker().writeUpdatedEntries(this.getTrackedValues());
        if(this.idTrackedValues != null)
            entity.farnutil_getIdDataTracker().writeUpdatedEntries(this.idTrackedValues);
    }

    @Override
    public @NotNull PacketType<LivingEntitySpawnPacket> getType() {
        return TYPE;
    }
}
