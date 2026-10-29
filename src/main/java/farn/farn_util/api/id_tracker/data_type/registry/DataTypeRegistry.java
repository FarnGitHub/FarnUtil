package farn.farn_util.api.id_tracker.data_type.registry;

import com.mojang.serialization.Lifecycle;
import farn.farn_util.FarnUtil;
import farn.farn_util.api.id_tracker.data_type.DataType;
import farn.farn_util.api.id_tracker.data_type.DataTypes;
import net.modificationstation.stationapi.api.event.registry.RegistryAttribute;
import net.modificationstation.stationapi.api.event.registry.RegistryAttributeHolder;
import net.modificationstation.stationapi.api.registry.Registries;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.registry.RegistryKey;
import net.modificationstation.stationapi.api.registry.SimpleRegistry;

import java.util.IdentityHashMap;
import java.util.Map;

public class DataTypeRegistry extends SimpleRegistry<DataType<?>> {
    public static final RegistryKey<Registry<DataType<?>>> KEY = RegistryKey.ofRegistry(FarnUtil.NAMESPACE.id("datatypes"));
    public static final DataTypeRegistry INSTANCE = Registries.create(KEY, new DataTypeRegistry(), Lifecycle.experimental());

    public DataTypeRegistry() {
        super(KEY, Lifecycle.experimental(), false);
        RegistryAttributeHolder.get(this).addAttribute(RegistryAttribute.SYNCED);
        RegistryAttributeHolder.get(this).addAttribute(RegistryAttribute.MODDED);
    }

    public void register(DataType<?> dataType) {
        add(RegistryKey.of(KEY, dataType.id()), dataType, Lifecycle.experimental());
    }
}
