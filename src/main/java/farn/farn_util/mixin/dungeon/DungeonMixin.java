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

@Mixin(value = DungeonFeature.class, priority = 1100)
public class DungeonMixin {

    @Unique
    private final Random farnutil_random = new Random();

    @SuppressWarnings("NameDoesntMatchTargetClass")
    @Inject(method="generate", at = @At("HEAD"))
    public void farnutil_setRandomSeed(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        farnutil_random.setSeed(world.getSeed() * (x + y + z));
    }

    @WrapMethod(method="getRandomChestItem")
    public ItemStack farnutil_getChestContent(Random rand, Operation<ItemStack> original) {
        ItemStack loot = original.call(rand);
        return loot != null ? loot : DungeonAPI.pickLoot(farnutil_random);
    }

    @WrapMethod(method="getRandomEntity")
    public String farnutil_getRandomEntity(Random rand, Operation<String> original) {
        String ogMob = original.call(rand);
        String mob = DungeonAPI.pickMob(farnutil_random);
        if(mob != null && farnutil_random.nextBoolean())
            return mob;
        else
            return ogMob;
    }
}
