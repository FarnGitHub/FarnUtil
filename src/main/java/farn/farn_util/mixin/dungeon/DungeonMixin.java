package farn.farn_util.mixin.dungeon;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import farn.farn_util.api.dungeon.DungeonAPI;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DungeonFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DungeonFeature.class)
public class DungeonMixin {

    @Unique
    private final Random farnutil_random = new Random();

    @Inject(method="generate", at = @At("HEAD"))
    public void farnutil_setRandomSeed(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        farnutil_random.setSeed(world.getSeed() * (x + y + z));
    }

    //When the loop stop run add all guaranteed loot
    @Definition(id="loopChestContent", local=@Local(type=int.class, ordinal=13))
    @Expression("loopChestContent < 8")
    @WrapOperation(method="generate", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean modifyChestContentLoop(int left, int right, Operation<Boolean> original, @Local(type=ChestBlockEntity.class) ChestBlockEntity chest) {
        boolean continued = original.call(left, right);
        if(!continued) {
            //use it own random to prevent messing up vanilla mob selection
            for(int loop = 0; loop < Math.min(19, DungeonAPI.getGuaranteedLootSize()); ++loop) {
                ItemStack stack = DungeonAPI.getGuaranteedLoot(loop).getStack(farnutil_random);
                if (stack != null)
                    chest.setStack(farnutil_random.nextInt(chest.size()), stack);
            }
        }
        return continued;
    }

    @WrapMethod(method="getRandomChestItem")
    public ItemStack getChestContent(Random rand, Operation<ItemStack> original) {
        ItemStack originalLoot = original.call(rand);
        ItemStack loot = DungeonAPI.getRandomLoots(farnutil_random);
        return loot != null ? loot : originalLoot;
    }

    @WrapMethod(method="getRandomEntity")
    public String getRandomEntity(Random rand, Operation<String> original) {
        String originalMob = original.call(rand);
        String mob = DungeonAPI.getRandomMob(farnutil_random);
        return mob != null && !mob.isEmpty() ? mob : originalMob;
    }
}
