package farn.farn_util.mixin.item_usage.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.farn_util.impl.item_usage.mixin_impl.MinecraftImpl;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method="tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 1, shift = At.Shift.BEFORE))
    public void farnutil_onStopUsingItem(CallbackInfo ci) {
        MinecraftImpl.handleStopUsingItem();
    }

    @WrapOperation(method="tick", at = {
            @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 1),
            @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 2),
            @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 3)}
    )
    public boolean farnutil_isUsingItemAndClick(Operation<Boolean> original) {
        return MinecraftImpl.preventSingleUseWhenUsing(original);
    }

    @WrapOperation(method="tick", at = {
            @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;focused:Z", ordinal = 1),
            @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;focused:Z", ordinal = 2)
    })
    public boolean farnutil_isNotUsingItem(Minecraft instance, Operation<Boolean> original) {
        return MinecraftImpl.preventSingleUseWhenUsing2(instance, original);
    }
}
