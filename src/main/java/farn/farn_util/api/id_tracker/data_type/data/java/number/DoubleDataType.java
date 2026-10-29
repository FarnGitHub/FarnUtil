package farn.farn_util.api.id_tracker.data_type.data.java.number;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleDataType implements DataType<Double> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Double> entry) throws IOException {
        output.writeDouble(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Double> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, stream.readDouble());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:double");
    }

    @Override
    public Class<Double> classType() {
        return Double.class;
    }
}
