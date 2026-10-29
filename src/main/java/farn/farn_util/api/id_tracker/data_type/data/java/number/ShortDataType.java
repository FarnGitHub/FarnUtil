package farn.farn_util.api.id_tracker.data_type.data.java.number;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortDataType implements DataType<Short> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Short> entry) throws IOException {
        output.writeShort(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Short> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, stream.readShort());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:short");
    }

    @Override
    public Class<Short> classType() {
        return Short.class;
    }

    @Override
    public Short defaultValue() {
        return 0;
    }
}
