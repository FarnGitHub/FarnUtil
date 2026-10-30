package farn.farn_util.api.id_tracker.data_type.data.minecraft;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NbtCompoundDataType implements DataType<NbtCompound> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<NbtCompound> entry) throws IOException {
        entry.get().write(output);
    }

    @Override
    public IDDataTrackerEntry<NbtCompound> readData(DataInputStream input, Identifier key) throws IOException {
        NbtCompound compound = new NbtCompound();
        compound.read(input);
        return new IDDataTrackerEntry<>(this, key, compound);
    }

    @Override
    public Identifier id() {
        return Identifier.of("nbt_compound");
    }

    @Override
    public Class<NbtCompound> classType() {
        return NbtCompound.class;
    }

    @Override
    public NbtCompound defaultValue() {
        return new NbtCompound();
    }
}
