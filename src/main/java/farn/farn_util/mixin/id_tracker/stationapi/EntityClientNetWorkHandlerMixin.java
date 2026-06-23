package farn.farn_util.mixin.id_tracker.stationapi;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.impl.id_tracker.SpawnDataProviderImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.impl.client.network.EntityClientNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityClientNetworkHandler.class)
public class EntityClientNetWorkHandlerMixin {

    @Definition(id="message", local=@Local(argsOnly = true, type= MessagePacket.class))
    @Definition(id="bytes", field="Lnet/modificationstation/stationapi/api/network/packet/MessagePacket;bytes:[B")
    @Definition(id="length", field="L_Dummy_$__Array__;length:I")
    @Expression("message.bytes.length")
    @WrapOperation(method="handleMobSpawn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static int farnutil_readIDTrackerMob(byte[] array, Operation<Integer> original, @Local(argsOnly = true, type= MessagePacket.class) MessagePacket message, @Local(type= LivingEntity.class) LivingEntity mob) {
        int modified = SpawnDataProviderImpl.readIDTrackerMob(message, mob);
        return modified >= 0 ? modified : original.call(array);
    }

    @Inject(method="handleEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;writeUpdatedEntries(Ljava/util/List;)V"))
    private static void farnutil_readIDTrackerEntity(PlayerEntity player, MessagePacket message, CallbackInfo ci, @Local(type= Entity.class) Entity entity) {
        SpawnDataProviderImpl.readIDTrackerEntity(message, entity);
    }

}
