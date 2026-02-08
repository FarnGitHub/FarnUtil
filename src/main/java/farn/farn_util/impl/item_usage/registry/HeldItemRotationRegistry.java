package farn.farn_util.impl.item_usage.registry;

import farn.farn_util.api.item_usage.HeldItemRotation;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Map;

public class HeldItemRotationRegistry {
    public static final HeldItemRotationRegistry instance = new HeldItemRotationRegistry();
    private final Map<Identifier, HeldItemRotation> heldItemRegistry = new Reference2ObjectOpenHashMap<>();

    public Identifier register(Identifier id, HeldItemRotation rotation) {
        heldItemRegistry.put(id, rotation);
        return id;
    }

    public void invokeHeldItemRotations(Identifier id, float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack) {
        HeldItemRotation heldItemRotation = heldItemRegistry.get(id);
        if(heldItemRotation != null) heldItemRotation.accept(id, tick, avgHeight, plr, heldStack);
    }

    private HeldItemRotationRegistry() {}
}
