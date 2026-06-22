package farn.farn_util.api.id_tracker;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.Vec3i;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class IDDataTracker {
    private static final Reference2IntOpenHashMap<Class<?>> DATA_TYPES = new Reference2IntOpenHashMap<>();
    private final Object2ObjectOpenHashMap<String, IDDataTrackerEntry> entries = new Object2ObjectOpenHashMap<>();
    private boolean dirty;

    public void startTracking(Identifier key, Object value) {
        startTracking(key.toString(), value);
    }

    public void startTracking(String key, Object value) {
        int type = DATA_TYPES.getInt(value.getClass());
        if (type < 0) {
            throw new IllegalArgumentException("Unknown data type: " + value.getClass());
        } else if (key.length() > 32767) {
            throw new IllegalArgumentException("Key too big! (Max is 32767)");
        } else if (this.entries.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate id value for " + key + "!");
        } else {
            this.entries.put(key, new IDDataTrackerEntry(type, key, value));
        }
    }


    //String getter start

    public byte getByte(String id) {
        return (Byte)(this.entries.get(id)).get();
    }

    public int getInt(String id) {
        return (Integer)(this.entries.get(id)).get();
    }

    public String getString(String id) {
        return (String)(this.entries.get(id)).get();
    }

    //String getter end

    //Identifier getter start

    public byte getByte(Identifier id) {
        return getByte(id.toString());
    }

    public int getInt(Identifier id) {
        return getInt(id.toString());
    }

    public String getString(Identifier id) {
        return getString(id.toString());
    }

    //Identifier getter end

    public void set(String id, Object object) {
        IDDataTrackerEntry entry = this.entries.get(id);
        if (!object.equals(entry.get())) {
            entry.set(object);
            entry.setDirty(true);
            this.dirty = true;
        }

    }

    @Environment(EnvType.SERVER)
    public boolean isDirty() {
        return this.dirty;
    }

    public static void writeEntries(List<IDDataTrackerEntry> entries, DataOutputStream output) throws IOException {
        if (entries != null) {
            output.writeInt(entries.size());
            for(IDDataTrackerEntry var3 : entries) {
                writeEntry(output, var3);
            }
        }
    }

    @Environment(EnvType.SERVER)
    public ArrayList<IDDataTrackerEntry> getDirtyEntries() {
        ArrayList<IDDataTrackerEntry> var1 = null;
        if (this.dirty) {
            for(IDDataTrackerEntry var3 : this.entries.values()) {
                if (var3.isDirty()) {
                    var3.setDirty(false);
                    if (var1 == null) {
                        var1 = new ArrayList<>();
                    }

                    var1.add(var3);
                }
            }
        }

        this.dirty = false;
        return var1;
    }

    public void writeAllEntries(DataOutputStream output) throws IOException {
        output.writeInt(this.entries.size());
        for(IDDataTrackerEntry entry : this.entries.values()) {
            writeEntry(output, entry);
        }
    }

    private static void writeEntry(DataOutputStream output, IDDataTrackerEntry entry) throws IOException {
        Packet.writeString(entry.getId(), output);
        output.writeByte(entry.getDataTypeId());
        switch (entry.getDataTypeId()) {
            case 0:
                output.writeByte((Byte)entry.get());
                break;
            case 1:
                output.writeShort((Short)entry.get());
                break;
            case 2:
                output.writeInt((Integer)entry.get());
                break;
            case 3:
                output.writeFloat((Float)entry.get());
                break;
            case 4:
                Packet.writeString((String)entry.get(), output);
                break;
            case 5:
                ItemStack stack = (ItemStack)entry.get();
                output.writeInt(stack.getItem().id);
                output.writeByte(stack.count);
                output.writeShort(stack.getDamage());
                break;
            case 6:
                Vec3i vec = (Vec3i)entry.get();
                output.writeInt(vec.x);
                output.writeInt(vec.y);
                output.writeInt(vec.z);
            case 7:
                output.writeDouble((Double) entry.get());
            case 8:
                output.writeLong((Long) entry.get());
        }

    }

    public static List<IDDataTrackerEntry> readEntries(DataInputStream input) throws IOException{
        List<IDDataTrackerEntry> list;

        int size = input.readInt();

        if(size <= 0) return null;
        else list = new ArrayList<>();

        for(int index = 0; index < size; index++) {
            String key = Packet.readString(input, 32767);
            int type = input.readByte();
            IDDataTrackerEntry entry = null;
            switch (type) {
                case 0:
                    entry = new IDDataTrackerEntry(type, key, input.readByte());
                    break;
                case 1:
                    entry = new IDDataTrackerEntry(type, key, input.readShort());
                    break;
                case 2:
                    entry = new IDDataTrackerEntry(type, key, input.readInt());
                    break;
                case 3:
                    entry = new IDDataTrackerEntry(type, key, input.readFloat());
                    break;
                case 4:
                    entry = new IDDataTrackerEntry(type, key, Packet.readString(input, 64));
                    break;
                case 5:
                    int itemId = input.readInt();
                    byte count = input.readByte();
                    short meta = input.readShort();
                    entry = new IDDataTrackerEntry(type, key, new ItemStack(itemId, count, meta));
                    break;
                case 6:
                    int x = input.readInt();
                    int y = input.readInt();
                    int z = input.readInt();
                    entry = new IDDataTrackerEntry(type, key, new Vec3i(x, y, z));
                    break;
                case 7:
                    entry = new IDDataTrackerEntry(type, key, input.readDouble());
                    break;
                case 8:
                    entry = new IDDataTrackerEntry(type, key, input.readLong());
            }

            list.add(entry);
        }

        return list;
    }

    @Environment(EnvType.CLIENT)
    public void writeUpdatedEntries(List<IDDataTrackerEntry> entries) {
        for(IDDataTrackerEntry var3 : entries) {
            IDDataTrackerEntry entry = this.entries.get(var3.getId());
            if (entry != null) {
                entry.set(var3.get());
            }
        }

    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    static {
        DATA_TYPES.defaultReturnValue(-1);
        DATA_TYPES.put(Byte.class, 0);
        DATA_TYPES.put(Short.class, 1);
        DATA_TYPES.put(Integer.class, 2);
        DATA_TYPES.put(Float.class, 3);
        DATA_TYPES.put(String.class, 4);
        DATA_TYPES.put(ItemStack.class, 5);
        DATA_TYPES.put(Vec3i.class, 6);
        DATA_TYPES.put(Double.class, 7);
        DATA_TYPES.put(Long.class, 8);
    }
}
