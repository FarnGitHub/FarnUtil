package farn.farn_util.api.id_tracker.data_type;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface DataType<T> {

    void writeData(DataOutputStream output, IDDataTrackerEntry<T> entry) throws IOException;

    IDDataTrackerEntry<T> readData(DataInputStream stream, Identifier key) throws IOException;

    Identifier id();

    Class<T> classType();

    default void writeDataRaw(DataOutputStream output, IDDataTrackerEntry<?> entry) throws IOException {
        //noinspection unchecked
        writeData(output, (IDDataTrackerEntry<T>) entry);
    }
}
