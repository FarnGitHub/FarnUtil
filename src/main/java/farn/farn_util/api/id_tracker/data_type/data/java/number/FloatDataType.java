package farn.farn_util.api.id_tracker.data_type.data.java.number;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatDataType implements DataType<Float> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Float> entry) throws IOException {
        output.writeFloat(entry.get());
    }

    @Override
    public IDDataTrackerEntry<Float> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, stream.readFloat());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:float");
    }

    @Override
    public Class<Float> classType() {
        return Float.class;
    }

    @Override
    public Float defaultValue() {
        return 0f;
    }

}
