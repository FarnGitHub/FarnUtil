package farn.farn_util.api.id_tracker.data_type.data.java;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BooleanDataType implements DataType<Boolean> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Boolean> entry) throws IOException {
        output.writeBoolean(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Boolean> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, stream.readBoolean());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:boolean");
    }

    @Override
    public Class<Boolean> classType() {
        return Boolean.class;
    }

    @Override
    public Boolean defaultValue() {
        return true;
    }
}
