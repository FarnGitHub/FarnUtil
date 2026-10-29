package farn.farn_util.api.id_tracker;

import farn.farn_util.api.id_tracker.data_type.DataType;
import farn.farn_util.api.id_tracker.data_type.registry.DataTypeRegistry;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class IDDataTracker {
    private final Reference2ObjectOpenHashMap<Identifier, IDDataTrackerEntry<?>> entries = new Reference2ObjectOpenHashMap<>();
    private boolean dirty;

    public <T> void startTracking(Identifier key, T value) {
        DataType<?> type = DataTypeRegistry.INSTANCE.get(value.getClass());
        if (type == null)
            throw new IllegalArgumentException("Unknown data type: " + value.getClass());
        else if (this.entries.containsKey(key))
            throw new IllegalArgumentException("Duplicate id value for " + key + "!");
        else
            startTracking(key, value, type);

    }

    public <T> void startTracking(Identifier key, T value, DataType<?> type) {
        if(!(type.classType().isAssignableFrom(value.getClass())))
            throw new IllegalArgumentException("No data type for: " + key);
        this.entries.put(key, new IDDataTrackerEntry<>(type, key, value));
    }

    public byte getByte(Identifier id) {
        return get(id);
    }

    public int getInt(Identifier id) {
        return get(id);
    }

    public String getString(Identifier id) {
        return get(id);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Identifier id) {
        return (T)entries.get(id).get();
    }

    public <T> void set(Identifier id, T object) {
        @SuppressWarnings("unchecked")
        IDDataTrackerEntry<T> entry = (IDDataTrackerEntry<T>)this.entries.get(id);
        if (!object.equals(entry.get())) {
            entry.set(object);
            entry.setDirty(true);
            this.dirty = true;
        }
    }

    public void set(String id, Object object) {
        this.set(Identifier.of(id), object);
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
                throw new RuntimeException("Unknown data type of " + id);
            IDDataTrackerEntry<?> entry = type.readData(input, id);
            list.add(entry);
        }

        return list;
    }


    public void writeUpdatedEntries(List<IDDataTrackerEntry<?>> entries) {
        for(IDDataTrackerEntry<?> data : entries) {
            IDDataTrackerEntry<?> entry = this.entries.get(data.getId());
            if (entry != null) entry.setRaw(data.get());
        }

    }

    public Collection<IDDataTrackerEntry<?>> values() {
        return this.entries.values();
    }

    @Deprecated
    public byte getByte(String id) {
        return getByte(Identifier.of(id));
    }

    @Deprecated
    public int getInt(String id) {
        return getInt(Identifier.of(id));
    }

    @Deprecated
    public String getString(String id) {
        return getString(Identifier.of(id));
    }

    @Deprecated
    public <T> T get(String id) {
        return get(Identifier.of(id));
    }

    @Deprecated
    public void startTracking(String key, Object value) {
        startTracking(Identifier.of(key), value);
    }
}
