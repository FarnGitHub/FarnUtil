package farn.farn_util.api.id_tracker;

import farn.farn_util.api.id_tracker.data_type.DataType;
import farn.farn_util.api.id_tracker.data_type.DataTypes;
import farn.farn_util.api.id_tracker.data_type.registry.DataTypeRegistry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.util.Identifier;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class IDDataTracker {
    private final Reference2ObjectOpenHashMap<Identifier, IDDataTrackerEntry<?>> entries = new Reference2ObjectOpenHashMap<>();
    private boolean dirty;

    public <T> void startTracking(Identifier id, DataType<T> type, @Nullable T value) {
        if(value == null)
            value = type.defaultValue();

        if (this.entries.containsKey(id))
            throw new IllegalArgumentException("Duplicate id value for " + id + "!");
        if(!DataTypeRegistry.INSTANCE.containsId(type.id()))
            throw new IllegalArgumentException(type.id() + " DataType hasn't been registered yet!");
        if(!(type.classType().isInstance(value)))
            throw new IllegalArgumentException("Cannot use " + type.id() + " DataType on " + id + "!");

        this.entries.put(id, new IDDataTrackerEntry<>(type, id, value));
    }

    public <T> T get(Identifier id) {
        IDDataTrackerEntry<?> entry = this.entries.get(id);
        if(entry == null)
            throw new IllegalArgumentException("No data with id " + id + "!");
        //noinspection unchecked
        return (T)entry.get();
    }

    public <T> void set(Identifier id, T value) {
        IDDataTrackerEntry<?> entry = this.entries.get(id);
        if(entry == null)
            throw new IllegalArgumentException("No data with id " + id + "!");
        if (!Objects.equals(value, entry.get())) {
            entry.setWithCheck(value);
            entry.setDirty(true);
            this.dirty = true;
        }
    }

    public void startTracking(Identifier id, byte value) {
        startTracking(id, DataTypes.BYTE, value);
    }

    public void startTracking(Identifier id, short value) {
        startTracking(id, DataTypes.SHORT, value);
    }

    public void startTracking(Identifier id, int value) {
        startTracking(id, DataTypes.INTEGER, value);
    }

    public void startTracking(Identifier id, long value) {
        startTracking(id, DataTypes.LONG, value);
    }

    public void startTracking(Identifier id, float value) {
        startTracking(id, DataTypes.FLOAT, value);
    }

    public void startTracking(Identifier id, double value) {
        startTracking(id, DataTypes.DOUBLE, value);
    }

    public void startTracking(Identifier id, boolean value) {
        startTracking(id, DataTypes.BOOLEAN, value);
    }

    public void startTracking(Identifier id, String value) {
        startTracking(id, DataTypes.STRING, value);
    }

    public byte getByte(Identifier id) {
        return get(id);
    }

    public short getShort(Identifier id) {
        return get(id);
    }

    public int getInt(Identifier id) {
        return get(id);
    }

    public long getLong(Identifier id) {
        return get(id);
    }

    public float getFloat(Identifier id) {
        return get(id);
    }

    public double getDouble(Identifier id) {
        return get(id);
    }

    public boolean getBoolean(Identifier id) {
        return get(id);
    }

    public String getString(Identifier id) {
        return get(id);
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public static void writeEntries(List<IDDataTrackerEntry<?>> entries, DataOutputStream output) throws IOException {
        if (entries != null) {
            output.writeInt(entries.size());
            for(IDDataTrackerEntry<?> entry : entries) {
                writeEntry(output, entry);
            }
        }
    }

    public ArrayList<IDDataTrackerEntry<?>> getDirtyEntries() {
        ArrayList<IDDataTrackerEntry<?>> list = new ArrayList<>();
        if (this.dirty) {
            for(IDDataTrackerEntry<?> entry : this.entries.values()) {
                if (entry.isDirty()) {
                    entry.setDirty(false);
                    list.add(entry);
                }
            }
        }

        this.dirty = false;
        return list;
    }

    public void writeAllEntries(DataOutputStream output) throws IOException {
        output.writeInt(this.entries.size());
        for(IDDataTrackerEntry<?> entry : this.entries.values()) {
           writeEntry(output, entry);
        }
    }

    private static void writeEntry(DataOutputStream output, IDDataTrackerEntry<?> entry) throws IOException {
        Packet.writeString(entry.getId().toString(), output);
        output.writeInt(DataTypeRegistry.INSTANCE.getRawId(entry.getType()));
        entry.getType().writeDataRaw(output, entry);
    }

    public static List<IDDataTrackerEntry<?>> readEntries(DataInputStream input) throws IOException{
        List<IDDataTrackerEntry<?>> list;

        int size = input.readInt();

        if(size <= 0) return Collections.emptyList();
        else list = new ArrayList<>();

        for(int index = 0; index < size; index++) {
            Identifier id = Identifier.of(Packet.readString(input, 32767));
            DataType<?> type = DataTypeRegistry.INSTANCE.get(input.readInt());
            if(type == null)
                throw new RuntimeException("Unknown data type for " + id);
            IDDataTrackerEntry<?> entry = type.readData(input, id);
            list.add(entry);
        }

        return list;
    }


    public void writeUpdatedEntries(List<IDDataTrackerEntry<?>> entries) {
        for(IDDataTrackerEntry<?> data : entries) {
            IDDataTrackerEntry<?> entry = this.entries.get(data.getId());
            if (entry != null) entry.setWithCheck(data.get());
        }

    }

    public Collection<IDDataTrackerEntry<?>> values() {
        return this.entries.values();
    }
}
