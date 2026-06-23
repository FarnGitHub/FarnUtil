package farn.farn_util.mixin.id_tracker.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.impl.id_tracker.SpawnDataProviderImpl;
import farn.farn_util.impl.id_tracker.network.LivingEntitySpawnPacket;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.LivingEntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkMixin {

    @Inject(method="onLivingEntitySpawn", at = @At("TAIL"))
    public void farnutil_onLivingEntitySpawn(LivingEntitySpawnS2CPacket packet, CallbackInfo ci, @Local(type= LivingEntity.class) LivingEntity living) {
        if(packet instanceof LivingEntitySpawnPacket packet2)
            SpawnDataProviderImpl.readVanillaIDTrackerMob(packet2, living);
    }
}
