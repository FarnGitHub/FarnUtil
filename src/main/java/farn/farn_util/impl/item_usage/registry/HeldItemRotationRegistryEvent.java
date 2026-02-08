package farn.farn_util.impl.item_usage.registry;

import farn.farn_util.api.item_usage.HeldItemRotation;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.util.Identifier;

public class HeldItemRotationRegistryEvent extends Event {
    public HeldItemRotationRegistry registry = HeldItemRotationRegistry.instance;

    public Identifier register(Identifier actionIdentifier, HeldItemRotation rotation) {
        return registry.register(actionIdentifier, rotation);
    }
}
