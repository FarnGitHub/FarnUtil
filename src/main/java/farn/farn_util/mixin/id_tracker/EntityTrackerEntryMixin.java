package farn.farn_util.mixin.id_tracker;

import farn.farn_util.api.id_tracker.IDDataTracker;
import farn.farn_util.impl.id_tracker.network.IDTrackerUpdatePacket;
import farn.farn_util.impl.id_tracker.network.IDTrackerInitPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.packet.Packet;
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
    public void idTracker_sendDirty(List<?> par1, CallbackInfo ci) {
        IDDataTracker idTracker = this.currentTrackedEntity.farnutil_getIdDataTracker();
        if(idTracker.isDirty()) {
            this.sendToAround(new IDTrackerUpdatePacket(this.currentTrackedEntity.id, idTracker));
        }
    }

    @Inject(method = "updateListener", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V", shift = At.Shift.AFTER, ordinal = 0))
    public void idTracker_createDataTracker(ServerPlayerEntity player, CallbackInfo ci) {
        this.sendToAround(new IDTrackerInitPacket(this.currentTrackedEntity));
    }

}
