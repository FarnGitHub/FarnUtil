package farn.farn_util.mixin.id_tracker.stationapi;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.impl.client.network.EntityClientNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Mixin(EntityClientNetworkHandler.class)
public class EntityClientNetWorkHandlerMixin {

    @Definition(id="message", local=@Local(argsOnly = true, type= MessagePacket.class))
    @Definition(id="bytes", field="Lnet/modificationstation/stationapi/api/network/packet/MessagePacket;bytes:[B")
    @Definition(id="length", field="L_Dummy_$__Array__;length:I")
    @Expression("message.bytes.length")
    @WrapOperation(method="handleMobSpawn", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static int farnutil_writeIDTrackerMob(byte[] array, Operation<Integer> original, @Local(argsOnly = true, type= MessagePacket.class) MessagePacket message, @Local(type= LivingEntity.class) LivingEntity mob) {
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

        return original.call(array);
    }

    @Inject(method="handleEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;writeUpdatedEntries(Ljava/util/List;)V"))
    private static void farnutil_writeIDTrackerEntity(PlayerEntity player, MessagePacket message, CallbackInfo ci, @Local(type= Entity.class) Entity entity) {
        try {
            int vaniilaDataSize = message.ints[4];
            List<IDDataTrackerEntry> data = IDDataTracker.readEntries(new DataInputStream(new ByteArrayInputStream(Arrays.copyOfRange(message.bytes, vaniilaDataSize, message.bytes.length))));
            if(data != null)
                entity.farnutil_getIdDataTracker().writeUpdatedEntries(data);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
