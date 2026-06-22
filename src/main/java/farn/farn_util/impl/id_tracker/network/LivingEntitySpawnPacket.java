package farn.farn_util.impl.id_tracker.network;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.DataTrackerEntry;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.MathHelper;
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

public class LivingEntitySpawnPacket extends Packet implements ManagedPacket<LivingEntitySpawnPacket> {
    public int id;
    public byte entityType;
    public int x;
    public int y;
    public int z;
    public byte yaw;
    public byte pitch;
    private DataTracker dataTracker;
    private List<DataTrackerEntry> trackedValues;
    private IDDataTracker idDataTracker;
    private List<IDDataTrackerEntry> idTrackedValues;

    public static final PacketType<LivingEntitySpawnPacket> TYPE = PacketType.builder(true, false, LivingEntitySpawnPacket::new).build();

    public LivingEntitySpawnPacket() {
    }

    public LivingEntitySpawnPacket(LivingEntity livingEntity) {
        this.id = livingEntity.id;
        this.entityType = (byte)EntityRegistry.getRawId(livingEntity);
        this.x = MathHelper.floor(livingEntity.x * 32.0);
        this.y = MathHelper.floor(livingEntity.y * 32.0);
        this.z = MathHelper.floor(livingEntity.z * 32.0);
        this.yaw = (byte)((int)(livingEntity.yaw * 256.0F / 360.0F));
        this.pitch = (byte)((int)(livingEntity.pitch * 256.0F / 360.0F));
        this.dataTracker = livingEntity.getDataTracker();
        this.idDataTracker = livingEntity.farnutil_getIdDataTracker();
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.id = stream.readInt();
            this.entityType = stream.readByte();
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
            this.yaw = stream.readByte();
            this.pitch = stream.readByte();
            this.trackedValues = DataTracker.readEntries(stream);
            this.idTrackedValues = IDDataTracker.readEntries(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.id);
            stream.writeByte(this.entityType);
            stream.writeInt(this.x);
            stream.writeInt(this.y);
            stream.writeInt(this.z);
            stream.writeByte(this.yaw);
            stream.writeByte(this.pitch);
            this.dataTracker.writeAllEntries(stream);
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
        if (this.trackedValues != null)
            entity.getDataTracker().writeUpdatedEntries(this.trackedValues);
        if(this.idTrackedValues != null)
            entity.farnutil_getIdDataTracker().writeUpdatedEntries(this.idTrackedValues);
    }

    @Override
    public int size() {
        return 20;
    }

    @Override
    public @NotNull PacketType<LivingEntitySpawnPacket> getType() {
        return TYPE;
    }
}
