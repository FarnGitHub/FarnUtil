package farn.farn_util.api.id_tracker.data_type.data.minecraft;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.minecraft.nbt.NbtElement;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NbtDataType implements DataType<NbtElement> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<NbtElement> entry) throws IOException {
        NbtElement.writeTag(entry.get(), output);
    }

    @Override
    public IDDataTrackerEntry<NbtElement> readData(DataInputStream stream, Identifier key) throws IOException {
        return new IDDataTrackerEntry<>(this, key, NbtElement.readTag(stream));
    }

    @Override
    public Identifier id() {
        return Identifier.of("nbt");
    }

    @Override
    public Class<NbtElement> classType() {
        return NbtElement.class;
    }
}
