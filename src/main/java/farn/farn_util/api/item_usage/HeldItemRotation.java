package farn.farn_util.api.item_usage;

import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public interface HeldItemRotation {

    void accept(Identifier actionIdentifier, float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack);
}
