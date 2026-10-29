package farn.farn_util.impl.id_tracker;

import farn.farn_util.api.id_tracker.data_type.DataType;
import farn.farn_util.api.id_tracker.data_type.DataTypes;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

public class Class2DataTypeLegacyMapper {
    private static final Reference2ObjectOpenHashMap<Class<?>, DataType<?>> MAP = new Reference2ObjectOpenHashMap<>();

    public static <T> DataType<T> get(@NotNull T object) {
        return (DataType<T>)MAP.get(object.getClass());
    }

    static {
        MAP.put(Byte.class, DataTypes.BYTE);
        MAP.put(Short.class, DataTypes.SHORT);
        MAP.put(Integer.class, DataTypes.INTEGER);
        MAP.put(Float.class, DataTypes.FLOAT);
        MAP.put(String.class, DataTypes.STRING);
        MAP.put(ItemStack.class, DataTypes.ITEMSTACK);
        MAP.put(Vec3i.class, DataTypes.VEC3I);
        MAP.put(Double.class, DataTypes.DOUBLE);
        MAP.put(Long.class, DataTypes.LONG);
        MAP.put(Boolean.class, DataTypes.BOOLEAN);
    }
}
