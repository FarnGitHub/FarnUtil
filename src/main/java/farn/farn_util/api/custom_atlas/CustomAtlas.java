package farn.farn_util.api.custom_atlas;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.modificationstation.stationapi.api.client.texture.SpritesheetHelper;
import net.modificationstation.stationapi.api.client.texture.atlas.*;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

import java.util.Collection;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class CustomAtlas implements SpritesheetHelper {
	private static final HashMap<Identifier, CustomAtlas> MAP = new HashMap<>();

	private final Int2ReferenceMap<Identifier> sprites = new Int2ReferenceOpenHashMap<>();
	private final ReferenceList<UnstitchAtlasSource.Region> regions = new ReferenceArrayList<>();
	private final Int2IntMap INDEXES = new Int2IntOpenHashMap();

	private final String texture;
	private final String id;
	private final boolean item;
	private AtlasSource source;

	private CustomAtlas(Identifier id, String texture, boolean item) {
		this.texture = texture;
		this.id = id.toString();
		this.item = item;
	}

	public static CustomAtlas of(Namespace ns, String texture, boolean item) {
		return MAP.computeIfAbsent(
				generateId(ns, texture, item),
				(id) -> new CustomAtlas(id, texture, item)
		);
	}

	public int getTextureIndex(int index) {
		return INDEXES.getOrDefault(index, 0);
	}

	private AtlasSource getAtlasSource() {
		makeSureAtlasReady();
		return source;
	}

	private void reload() {
		makeSureAtlasReady();

		INDEXES.clear();

		ExpandableAtlas atlas = item ? Atlases.getGuiItems() : Atlases.getTerrain();

		ImmutableList<Atlas.Sprite> itemSprites = atlas.addSpritesheet(16, this);

		for (int i = 0; i < itemSprites.size(); i++) {
			INDEXES.put(i, itemSprites.get(i).index);
		}
	}

	private void makeSureAtlasReady() {
		if(regions.isEmpty())
			for (int y = 0; y < 16; y++) for (int x = 0; x < 16; x++) {
				int textureIndex = y * 16 + x;

				Identifier identifier = Identifier.of(id + "_" + textureIndex);

				sprites.put(textureIndex, identifier);

				regions.add(new UnstitchAtlasSource.Region(identifier, x, y, 1, 1));
			}

		if(source == null)
			source = new UnstitchAtlasSource(Identifier.of(texture), regions, 16, 16);

	}

	private static Identifier generateId(Namespace ns, String texture, boolean item) {
		String basePath = item ? "item" : "block";
		texture = texture.replace(" ", "_");
		if(texture.startsWith("/"))
			texture = texture.substring(1);
		texture = texture.replace(".png", "");
		return ns.id(basePath + "/" + texture);
	}

	public static Collection<AtlasSource> getAtlasSources() {
		return values().stream().map(CustomAtlas::getAtlasSource).toList();
	}

	public static void reloads() {
		values().forEach(CustomAtlas::reload);
	}

	public static Collection<CustomAtlas> values() {
		return MAP.values();
	}

	@Override
	public Identifier generateIdentifier(int i) {
		return sprites.get(i);
	}

	@Override
	public IntIntPair getResolutionMultiplier(int i) {
		return DEFAULT_RESOLUTION_MULTIPLIER;
	}
}
