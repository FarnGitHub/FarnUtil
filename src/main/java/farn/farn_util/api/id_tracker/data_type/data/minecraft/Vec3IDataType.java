package farn.farn_util.api.id_tracker.data_type.data.minecraft;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.minecraft.util.math.Vec3i;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Vec3IDataType implements DataType<Vec3i> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Vec3i> entry) throws IOException {
        Vec3i vec3i = entry.get();
        output.writeInt(vec3i.x);
        output.writeInt(vec3i.y);
        output.writeInt(vec3i.z);
    }

    @Override
    public IDDataTrackerEntry<Vec3i> readData(DataInputStream stream, Identifier key) throws IOException {
        int x = stream.readInt();
        int y = stream.readInt();
        int z = stream.readInt();
        return new IDDataTrackerEntry<>(this, key, new Vec3i(x, y, z));
    }

    @Override
    public Identifier id() {
        return Identifier.of("vec3i");
    }

    @Override
    public Class<Vec3i> classType() {
        return Vec3i.class;
    }
}
