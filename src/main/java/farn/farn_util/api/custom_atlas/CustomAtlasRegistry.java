package farn.farn_util.api.custom_atlas;

import com.mojang.serialization.Lifecycle;
import farn.farn_util.FarnUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.client.texture.atlas.AtlasSource;
import net.modificationstation.stationapi.api.registry.Registries;
import net.modificationstation.stationapi.api.registry.RegistryKey;
import net.modificationstation.stationapi.api.registry.SimpleRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CustomAtlasRegistry extends SimpleRegistry<CustomAtlas> {
    public static final RegistryKey<CustomAtlasRegistry> KEY = RegistryKey.ofRegistry(FarnUtil.NAMESPACE.id("custom_atlas"));
    public static final CustomAtlasRegistry INSTANCE = Registries.create(KEY, new CustomAtlasRegistry(), Lifecycle.experimental());

    public CustomAtlasRegistry() {
        super(KEY, Lifecycle.experimental(), false);
    }

    public static Collection<AtlasSource> generateAllSource() {
        List<AtlasSource> sources = new ArrayList<>();
        INSTANCE.forEach((atlas) -> {
            sources.add(atlas.generateAtlas());
        });
        return sources;
    }

    public static void reloadAll() {
        INSTANCE.forEach(CustomAtlas::registerTextures);
    }
}
