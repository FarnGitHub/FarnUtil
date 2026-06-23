package farn.farn_util.mixin.id_tracker.stationapi.provider;

import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.impl.id_tracker.SpawnDataProviderImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.StationSpawnDataProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobSpawnDataProvider.class)
public interface MobSpawnDataProviderMixin extends StationSpawnDataProvider {

    @Inject(method="getSpawnData", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/server/entity/MobSpawnDataProvider;writeToMessage(Lnet/modificationstation/stationapi/api/network/packet/MessagePacket;)V", shift = At.Shift.BEFORE))
    default void beforeMessageWrite(CallbackInfoReturnable<Packet> cir, @Local(type= MessagePacket.class) MessagePacket message, @Local(type= LivingEntity.class) LivingEntity mob, @Local(type=byte[].class, ordinal = 1) byte[] vanillaData) {
        SpawnDataProviderImpl.beforeMobMessageWrite(message, mob, vanillaData);
    }
}
