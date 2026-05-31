package farn.farn_util.mixin.item_usage.client.player;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class PlayerItemTextureMixin {

    @ModifyReturnValue(method="getItemStackTextureId", at = @At("RETURN"))
    public int farnutil_useItemUsingTexture(int original, @Local(argsOnly = true) ItemStack stack) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        int usingTexture = stack.getItem().farnutil_getUsingTexture(player, stack, player.farnutil_getUsingDuration());
        return usingTexture < 0 ? original : usingTexture;
    }
}
