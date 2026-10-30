package farn.farn_util.api.id_tracker.data_type.data.minecraft;

import farn.farn_util.api.id_tracker.IDDataTrackerEntry;
import farn.farn_util.api.id_tracker.data_type.DataType;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Vec3DDataType implements DataType<Vec3d> {
    @Override
    public void writeData(DataOutputStream output, IDDataTrackerEntry<Vec3d> entry) throws IOException {
        Vec3d vec3d = entry.get();
        output.writeDouble(vec3d.x);
        output.writeDouble(vec3d.y);
        output.writeDouble(vec3d.z);
    }

    @Override
    public IDDataTrackerEntry<Vec3d> readData(DataInputStream input, Identifier key) throws IOException {
        Vec3d vec3d = Vec3d.createCached(input.readDouble(), input.readDouble(), input.readDouble());
        return new IDDataTrackerEntry<>(this, key, vec3d);
    }

    @Override
    public Identifier id() {
        return Identifier.of("vec3d");
    }

    @Override
    public Class<Vec3d> classType() {
        return Vec3d.class;
    }

    @Override
    public Vec3d defaultValue() {
        return Vec3d.createCached(0,0,0);
    }
}
