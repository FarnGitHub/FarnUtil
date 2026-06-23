package farn.farn_util.mixin.id_tracker.stationapi.provider;

import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.impl.id_tracker.SpawnDataProviderImpl;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySpawnDataProvider.class)
public interface EntitySpawnDataProviderMixin {

    @Shadow
    boolean syncTrackerAtSpawn();

    @Inject(method="getSpawnData", at = @At(value = "INVOKE", target = "Lnet/modificationstation/stationapi/api/server/entity/EntitySpawnDataProvider;writeToMessage(Lnet/modificationstation/stationapi/api/network/packet/MessagePacket;)V", shift = At.Shift.BEFORE))
    default void beforeMessageWrite(CallbackInfoReturnable<Packet> cir, @Local(type= MessagePacket.class) MessagePacket message, @Local(type= Entity.class) Entity mob) {
        SpawnDataProviderImpl.beforeEntityMessageWrite(message, mob, syncTrackerAtSpawn());
    }
}
