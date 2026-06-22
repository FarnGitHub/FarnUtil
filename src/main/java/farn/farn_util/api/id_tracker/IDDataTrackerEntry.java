package farn.farn_util.api.id_tracker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class IDDataTrackerEntry {
    private final int dataTypeId;
    private final String id;
    private Object value;
    private boolean dirty;

    public IDDataTrackerEntry(int dataTypeId, String id, Object value) {
        super();
        this.id = id;
        this.value = value;
        this.dataTypeId = dataTypeId;
        this.dirty = true;
    }

    public String getId() {
        return this.id;
    }

    public void set(Object value) {
        this.value = value;
    }

    public Object get() {
        return this.value;
    }

    public int getDataTypeId() {
        return this.dataTypeId;
    }

    @Environment(EnvType.SERVER)
    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
