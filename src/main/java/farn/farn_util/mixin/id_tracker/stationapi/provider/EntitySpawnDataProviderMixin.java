package farn.farn_util.mixin.id_tracker.stationapi.provider;

import com.google.common.primitives.Bytes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Mixin(EntitySpawnDataProvider.class)
public interface EntitySpawnDataProviderMixin {

    @Shadow
    boolean syncTrackerAtSpawn();

    @Inject(method="getSpawnData", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/server/entity/EntitySpawnDataProvider;writeToMessage(Lnet/modificationstation/stationapi/api/network/packet/MessagePacket;)V", shift = At.Shift.BEFORE))
    default void beforeMessageWrite(CallbackInfoReturnable<Packet> cir, @Local(type= MessagePacket.class) MessagePacket message, @Local(type= Entity.class) Entity mob) {
        message.ints = Arrays.copyOf(message.ints, 6);
        message.ints[5] = message.bytes.length;
        if(!syncTrackerAtSpawn()) return;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mob.farnutil_getIdDataTracker().writeAllEntries(new DataOutputStream(stream));
            byte[] idData = stream.toByteArray();
            message.bytes = Bytes.concat(message.bytes, idData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
