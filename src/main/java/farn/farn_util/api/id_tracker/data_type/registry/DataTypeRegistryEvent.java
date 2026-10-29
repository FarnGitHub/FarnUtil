package farn.farn_util.api.id_tracker.data_type.registry;

import farn.farn_util.api.id_tracker.data_type.DataType;
import net.mine_diver.unsafeevents.Event;

public class DataTypeRegistryEvent extends Event {

    public void register(DataType<?> dataType) {
        DataTypeRegistry.INSTANCE.register(dataType);
    }
}
