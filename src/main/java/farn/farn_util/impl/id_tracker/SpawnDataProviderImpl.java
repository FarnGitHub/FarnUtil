package farn.farn_util.impl.id_tracker;

import com.google.common.primitives.Bytes;
import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.impl.id_tracker.network.LivingEntitySpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SpawnDataProviderImpl {

    public static void beforeMobMessageWrite(MessagePacket message, LivingEntity mob, byte[] vanillaData) {
        message.ints = Arrays.copyOf(message.ints, 5);
        message.ints[4] = 2 + vanillaData.length;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mob.farnutil_getIdDataTracker().writeAllEntries(new DataOutputStream(stream));
            byte[] idData = stream.toByteArray();
            message.bytes = Bytes.concat(message.bytes, idData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void beforeEntityMessageWrite(MessagePacket message, Entity mob, boolean syncTracker) {
        message.ints = Arrays.copyOf(message.ints, 6);
        message.ints[5] = message.bytes != null ? message.bytes.length : -1;
        if(!syncTracker || message.bytes == null) return;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mob.farnutil_getIdDataTracker().writeAllEntries(new DataOutputStream(stream));
            byte[] idData = stream.toByteArray();
            message.bytes = Bytes.concat(message.bytes, idData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int readIDTrackerMob(MessagePacket message, LivingEntity mob) {
        try {
            int vaniilaDataSize = message.ints[4];
            List<IDDataTrackerEntry> data = IDDataTracker.readEntries(new DataInputStream(new ByteArrayInputStream(Arrays.copyOfRange(message.bytes, vaniilaDataSize, message.bytes.length))));
            if(data != null)
                mob.farnutil_getIdDataTracker().writeUpdatedEntries(data);
            return vaniilaDataSize;
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public static void readIDTrackerEntity(MessagePacket message, Entity entity) {
        try {
            int vanillaDataSize = message.ints[5];
            List<IDDataTrackerEntry> data = IDDataTracker.readEntries(new DataInputStream(new ByteArrayInputStream(Arrays.copyOfRange(message.bytes, vanillaDataSize, message.bytes.length))));
            if(data != null)
                entity.farnutil_getIdDataTracker().writeUpdatedEntries(data);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readVanillaIDTrackerMob(LivingEntitySpawnPacket packet, LivingEntity living) {
        if(packet.idTrackedValues != null)
            living.farnutil_getIdDataTracker().
                    writeUpdatedEntries(packet.idTrackedValues);
    }


}
