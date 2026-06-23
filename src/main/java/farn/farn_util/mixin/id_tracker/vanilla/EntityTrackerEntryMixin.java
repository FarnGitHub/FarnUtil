package farn.farn_util.mixin.id_tracker.vanilla;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.impl.id_tracker.network.EntityIDTrackerUpdatePacket;
import farn.farn_util.impl.id_tracker.network.LivingEntitySpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.LivingEntitySpawnS2CPacket;
import net.minecraft.server.entity.EntityTrackerEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityTrackerEntry.class)
public abstract class EntityTrackerEntryMixin {

    @Shadow
    public Entity currentTrackedEntity;

    @Shadow
    public abstract void sendToAround(Packet packet);

    @Inject(method="notifyNewLocation", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;isDirty()Z", shift = At.Shift.BEFORE))
    public void update_idTracker(List<?> par1, CallbackInfo ci) {
        IDDataTracker idTracker = this.currentTrackedEntity.farnutil_getIdDataTracker();
        if(idTracker.isDirty()) {
            this.sendToAround(new EntityIDTrackerUpdatePacket(this.currentTrackedEntity.id, idTracker));
        }
    }

    @Definition(id="LivingEntitySpawnS2CPacket", type = LivingEntitySpawnS2CPacket.class)
    @Expression("new LivingEntitySpawnS2CPacket(?)")
    @WrapOperation(method="createAddEntityPacket", at = @At("MIXINEXTRAS:EXPRESSION"))
    public LivingEntitySpawnS2CPacket farnutil_reDirectLivingEntityPacker(LivingEntity living, Operation<LivingEntitySpawnS2CPacket> original) {
        return new LivingEntitySpawnPacket(living);
    }
}
