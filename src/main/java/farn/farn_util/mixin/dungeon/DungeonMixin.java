package farn.farn_util.mixin.dungeon;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.farn_util.api.dungeon.DungeonAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DungeonFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = DungeonFeature.class, priority = Integer.MAX_VALUE)
public class DungeonMixin {

    @Unique
    private World farnutil_world;

    @SuppressWarnings("NameDoesntMatchTargetClass")
    @Inject(method="generate", at = @At("HEAD"))
    public void farnutil_setRandomSeed(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        farnutil_world = world;
    }

    @WrapMethod(method="getRandomChestItem")
    public ItemStack farnutil_getChestContent(Random rand, Operation<ItemStack> original) {
        return DungeonAPI.getLoot(original.call(rand), rand, farnutil_world);
    }

    @WrapMethod(method="getRandomEntity")
    public String farnutil_getRandomEntity(Random rand, Operation<String> original) {
        return DungeonAPI.getMob(original.call(rand), rand, farnutil_world);
    }
}
