package farn.farn_util.api.id_tracker.data_type.data.java.number;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteDataType implements DataType<Byte> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Byte> entry) throws IOException {
        output.writeByte(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Byte> readData(DataInputStream input, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, input.readByte());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:byte");
    }

    @Override
    public Class<Byte> classType() {
        return Byte.class;
    }

    @Override
    public Byte defaultValue() {
        return 0;
    }
}
