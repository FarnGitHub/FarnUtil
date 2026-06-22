package farn.farn_util.mixin.id_tracker.stationapi.provider;

import com.google.common.primitives.Bytes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.StationSpawnDataProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Mixin(MobSpawnDataProvider.class)
public interface MobSpawnDataProviderMixin extends StationSpawnDataProvider {

    @Inject(method="getSpawnData", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/server/entity/MobSpawnDataProvider;writeToMessage(Lnet/modificationstation/stationapi/api/network/packet/MessagePacket;)V", shift = At.Shift.BEFORE))
    default void beforeMessageWrite(CallbackInfoReturnable<Packet> cir, @Local(type= MessagePacket.class) MessagePacket message, @Local(type= LivingEntity.class) LivingEntity mob, @Local(type=byte[].class, ordinal = 1) byte[] vanillaData) {
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
}
