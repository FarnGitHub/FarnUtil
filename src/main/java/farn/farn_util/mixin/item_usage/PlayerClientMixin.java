package farn.farn_util.mixin.item_usage;

import net.minecraft.client.input.Input;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class PlayerClientMixin extends PlayerEntity {
    @Shadow
    public Input input;

    public PlayerClientMixin(World world) {
        super(world);
    }

    @Override
    public float UseApi_getFovMultiplier() {
        return UseApi_getUsingItem() != null ? UseApi_getUsingItem().getItem().UseApi_getFovMultiplier(this, UseApi_getUsingItem(), UseApi_getUsingDuration()) : 1.0F;
    }

    @Inject(method="tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;update(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    public void getCustomWalkSpeedMultiplier(CallbackInfo ci) {
        if(UseApi_hasUsingItem() && !hasVehicle()) {
            float walkSpeedMultiplier = UseApi_getWalkSpeedMultiplier();
            this.input.movementSideways *= walkSpeedMultiplier;
            this.input.movementForward *= walkSpeedMultiplier;
        }
    }
}
