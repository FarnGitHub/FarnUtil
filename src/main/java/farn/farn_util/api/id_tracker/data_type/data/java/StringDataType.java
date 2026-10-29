package farn.farn_util.api.id_tracker.data_type.data.java;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringDataType implements DataType<String> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<String> entry) throws IOException {
        output.writeUTF(entry.get());
    }

    @Override
    public IDDataTrackerEntry<String> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, stream.readUTF());
    }

    @Override
    public Identifier id() {
        return Identifier.of("java:string");
    }

    @Override
    public Class<String> classType() {
        return String.class;
    }

    @Override
    public String defaultValue() {
        return "";
    }

}
