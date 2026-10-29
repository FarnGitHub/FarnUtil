package farn.farn_util.api.id_tracker.data_type.data.java.number;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LongDataType implements DataType<Long> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Long> entry) throws IOException {
        output.writeLong(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Long> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, stream.readLong());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:long");
    }

    @Override
    public Class<Long> classType() {
        return Long.class;
    }

    @Override
    public Long defaultValue() {
        return 0L;
    }

}
