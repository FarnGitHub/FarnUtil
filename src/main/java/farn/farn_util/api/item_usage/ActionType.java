package farn.farn_util.api.item_usage;

import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class ActionType {

    public final Identifier id;

    public ActionType(Identifier id) {
        this.id = id;
    }

    public abstract void applyFirstPersonItemRotation(float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack);

    public abstract void applyThirdPersonItemRotation(float tick, PlayerEntity plr, ItemStack heldStack);
}
