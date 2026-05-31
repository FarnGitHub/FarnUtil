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
import net.minecraft.world.gen.feature.DungeonFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(DungeonFeature.class)
public class DungeonMixin {

    //When the loop stop run add all guaranteed loot
    @Definition(id="loopChestContent", local=@Local(type=int.class, ordinal=13))
    @Expression("loopChestContent < 8")
    @WrapOperation(method="generate", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean modifyChestContentLoop(int left, int right, Operation<Boolean> original, @Local(type=ChestBlockEntity.class) ChestBlockEntity chestEntity, @Local(argsOnly = true, type = Random.class) Random random) {
        boolean continued = original.call(left, right);
        if(!continued) {
            for(int loop = 0; loop < Math.min(19, DungeonAPI.getGuaranteedLootSize()); ++loop) {
                ItemStack stack = DungeonAPI.getGuaranteedLoot(loop).getStack();
                if (stack != null)
                    chestEntity.setStack(random.nextInt(chestEntity.size()), stack);
            }
        }
        return continued;
    }

    @WrapMethod(method="getRandomChestItem")
    public ItemStack getChestContent(Random rand, Operation<ItemStack> original) {
        return DungeonAPI.getRandomLoots(rand);
    }

    @WrapMethod(method="getRandomEntity")
    public String getRandomEntity(Random rand, Operation<ItemStack> original) {
        return DungeonAPI.getRandomMob(rand);
    }
}
