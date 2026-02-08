package farn.farn_util.mixin.item_usage.common;

import farn.farn_util.impl.item_usage.interfaces_impl.ExtendedItemUsage;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements ExtendedItemUsage {
}
