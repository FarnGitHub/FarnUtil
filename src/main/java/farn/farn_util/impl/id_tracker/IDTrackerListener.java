package farn.farn_util.impl.id_tracker;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.id_tracker.data_type.DataTypes;
import farn.farn_util.api.id_tracker.data_type.data.minecraft.ItemStackDataType;
import farn.farn_util.api.id_tracker.data_type.data.minecraft.NbtDataType;
import farn.farn_util.api.id_tracker.data_type.data.java.StringDataType;
import farn.farn_util.api.id_tracker.data_type.data.minecraft.Vec3IDataType;
import farn.farn_util.api.id_tracker.data_type.data.java.BooleanDataType;
import farn.farn_util.api.id_tracker.data_type.data.java.number.*;
import farn.farn_util.api.id_tracker.data_type.registry.DataTypeRegistryEvent;
import farn.farn_util.impl.id_tracker.network.IDTrackerInitPacket;
import farn.farn_util.impl.id_tracker.network.IDTrackerUpdatePacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;

public class IDTrackerListener {

    @EventListener
    public void registerPacket(PacketRegisterEvent event) {
        StationAPI.EVENT_BUS.post(new DataTypeRegistryEvent());
        event.register(FarnUtil.NAMESPACE.id("entity_tracker_update"), IDTrackerUpdatePacket.TYPE);
        event.register(FarnUtil.NAMESPACE.id("living_spawn"), IDTrackerInitPacket.TYPE);
    }

    @EventListener
    public void registerDataTypes(DataTypeRegistryEvent event) {
        event.register(DataTypes.BYTE = new ByteDataType());
        event.register(DataTypes.SHORT = new ShortDataType());
        event.register(DataTypes.INTEGER = new IntegerDataType());
        event.register(DataTypes.FLOAT = new FloatDataType());
        event.register(DataTypes.DOUBLE = new DoubleDataType());
        event.register(DataTypes.LONG = new LongDataType());
        event.register(DataTypes.STRING = new StringDataType());
        event.register(DataTypes.BOOLEAN = new BooleanDataType());
        event.register(DataTypes.ITEMSTACK = new ItemStackDataType());
        event.register(DataTypes.VEC3I = new Vec3IDataType());
        event.register(DataTypes.NBT = new NbtDataType());
    }
}
