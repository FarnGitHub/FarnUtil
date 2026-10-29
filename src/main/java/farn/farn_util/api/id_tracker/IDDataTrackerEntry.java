package farn.farn_util.api.id_tracker;

import farn.farn_util.api.id_tracker.data_type.DataType;
import net.modificationstation.stationapi.api.util.Identifier;

public class IDDataTrackerEntry<T> {
    private final DataType<?> type;
    private final Identifier id;
    private T value;
    private boolean dirty;

    public IDDataTrackerEntry(DataType<?> type, Identifier id, T value) {
        super();
        this.id = id;
        this.value = value;
        this.type = type;
        this.dirty = true;
    }

    public Identifier getId() {
        return this.id;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public DataType<?> getType() {
        return this.type;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setRaw(Object value) {
        //noinspection unchecked
        this.set((T) value);
    }
}
