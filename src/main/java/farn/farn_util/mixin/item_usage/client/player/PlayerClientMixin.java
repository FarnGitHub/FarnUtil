package farn.farn_util.mixin.item_usage.client.player;

import net.minecraft.client.input.Input;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPlayerEntity.class, priority = 900)
public abstract class PlayerClientMixin extends PlayerEntity {
    @Shadow
    public Input input;

    public PlayerClientMixin(World world) {
        super(world);
    }

    @Override
    public float farnutil_getFovMultiplier() {
        return farnutil_getUsingItem() != null ? farnutil_getUsingItem().getItem().farnutil_getFovMultiplier(this, farnutil_getUsingItem(), farnutil_getUsingDuration()) : 1.0F;
    }

    @Inject(method="tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;update(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    public void getCustomWalkSpeedMultiplier(CallbackInfo ci) {
        if(farnutil_isUsingItem() && !hasVehicle()) {
            float walkSpeedMultiplier = farnutil_getWalkSpeedMultiplier();
            this.input.movementSideways *= walkSpeedMultiplier;
            this.input.movementForward *= walkSpeedMultiplier;
        }
    }
}
