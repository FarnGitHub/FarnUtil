package farn.farn_util.mixin.item_usage;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.farn_util.impl.item_usage.ItemUsageAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public ClientPlayerEntity player;
    @Unique
    public boolean useApi_canClick = true;

    @Inject(method="tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 1, shift = At.Shift.BEFORE))
    public void checkBrabBrah(CallbackInfo ci) {
        if(player.UseApi_hasUsingItem()) {
            if(!Mouse.isButtonDown(1)) {
                ItemUsageAPI.stopUsingItemClient(player);
            }
            useApi_canClick = false;
        } else {
            useApi_canClick = true;
        }
    }

    @WrapOperation(method="tick", at = {
            @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 1),
            @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 2),
            @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 3)}
    )
    public boolean cancelDADSASDA(Operation<Boolean> original) {
        return useApi_canClick && original.call();
    }

    @WrapOperation(method="tick", at = {
            @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;focused:Z", ordinal = 1),
            @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;focused:Z", ordinal = 2)
    })
    public boolean dasdadasda(Minecraft instance, Operation<Boolean> original) {
        return !player.UseApi_hasUsingItem() && original.call(instance);
    }
}
