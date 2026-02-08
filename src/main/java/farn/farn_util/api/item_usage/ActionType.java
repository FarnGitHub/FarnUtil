package farn.farn_util.api.item_usage;

import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class ActionType {

    public final Identifier id;

    public ActionType(Identifier id) {
        this.id = id;
    }

    public abstract void applyHeldItemRotation(float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack);
}
