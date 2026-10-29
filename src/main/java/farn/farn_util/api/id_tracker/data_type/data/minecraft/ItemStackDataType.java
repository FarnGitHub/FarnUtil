package farn.farn_util.api.id_tracker.data_type.data.minecraft;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ItemStackDataType implements DataType<ItemStack> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<ItemStack> entry) throws IOException {
        boolean notNull = entry.get() != null;
        output.writeBoolean(notNull);
        if(notNull) {
            entry.get().writeNbt(new NbtCompound()).write(output);
        }
    }

    @Override
    public IDDataTrackerEntry<ItemStack> readData(DataInputStream stream, Identifier key) throws IOException {
        if(stream.readBoolean()) {
            NbtCompound itemData = new NbtCompound();
            itemData.read(stream);
            return new IDDataTrackerEntry<>(this, key, new ItemStack(itemData));
        }
        return null;
    }

    @Override
    public Identifier id() {
        return Identifier.of("itemstack");
    }

    @Override
    public Class<ItemStack> classType() {
        return ItemStack.class;
    }

    @Override
    public ItemStack defaultValue() {
        return null;
    }
}
