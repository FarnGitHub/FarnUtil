package farn.farn_util.mixin.id_tracker;

import com.google.common.primitives.Bytes;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.StationSpawnDataProvider;
import org.spongepowered.asm.mixin.Mixin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static net.modificationstation.stationapi.api.StationAPI.NAMESPACE;
import static net.modificationstation.stationapi.api.util.Identifier.of;

@Mixin(MobSpawnDataProvider.class)
public interface MobSpawnDataProviderMixin extends StationSpawnDataProvider {

    @WrapMethod(method="getSpawnData")
    default Packet getSpawnData(Operation<Packet> original) {
        LivingEntity mob = (LivingEntity) this;
        MessagePacket message = new MessagePacket(of(NAMESPACE, "spawn_mob"));
        message.strings = new String[] { getHandlerIdentifier().toString() };
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mob.getDataTracker().writeAllEntries(new DataOutputStream(outputStream));
        byte[] data = outputStream.toByteArray();
        message.ints = new int[] {
                mob.id,
                MathHelper.floor(mob.x * 32.0D),
                MathHelper.floor(mob.y * 32.0D),
                MathHelper.floor(mob.z * 32.0D),
                2 + data.length
        };
        byte[] rotations = new byte[] {
                (byte)((int)(mob.yaw * 256.0F / 360.0F)),
                (byte)((int)(mob.pitch * 256.0F / 360.0F))
        };
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        try {
            mob.farnutil_getIdDataTracker().writeAllEntries(new DataOutputStream(outputStream2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] data2 = outputStream2.toByteArray();
        message.bytes = Bytes.concat(rotations, data, data2);
        writeToMessage(message);
        return message;
    }
}
