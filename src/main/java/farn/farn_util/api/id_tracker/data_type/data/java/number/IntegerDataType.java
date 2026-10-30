package farn.farn_util.api.id_tracker.data_type.data.java.number;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntegerDataType implements DataType<Integer> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Integer> entry) throws IOException {
        output.writeInt(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Integer> readData(DataInputStream input, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, input.readInt());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:integer");
    }

    @Override
    public Class<Integer> classType() {
        return Integer.class;
    }

    @Override
    public Integer defaultValue() {
        return 0;
    }
}
