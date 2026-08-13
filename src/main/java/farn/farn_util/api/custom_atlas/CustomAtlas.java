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

@Environment(EnvType.CLIENT)
public class CustomAtlas implements SpritesheetHelper {
	private final Int2ReferenceMap<Identifier> sprites = new Int2ReferenceOpenHashMap<>();
	private final ReferenceList<UnstitchAtlasSource.Region> regions = new ReferenceArrayList<>();
	private final Int2IntMap INDEXES = new Int2IntOpenHashMap();

	private final String texture;
	private final String baseId;
	private final boolean useItemAtlas;

	public CustomAtlas(String texture, Identifier baseId, boolean useItemAtlas) {
		this.texture = texture;
		this.baseId = baseId.toString();
		this.useItemAtlas = useItemAtlas;
	}

	public AtlasSource generateAtlas() {
		if(regions.isEmpty())
			generateRegions();
		return new UnstitchAtlasSource(Identifier.of(texture), regions, 16, 16);
	}

	private void generateRegions() {
		for (int y = 0; y < 16; y++) for (int x = 0; x < 16; x++) {
			int textureIndex = y * 16 + x;

			Identifier identifier = Identifier.of(baseId + "_" + textureIndex);

			sprites.put(textureIndex, identifier);

			regions.add(new UnstitchAtlasSource.Region(identifier, x, y, 1, 1));
		}
	}

	public void registerTextures() {
		INDEXES.clear();

		ExpandableAtlas itemAtlas = useItemAtlas ? Atlases.getGuiItems() : Atlases.getTerrain();

		ImmutableList<Atlas.Sprite> itemSprites = itemAtlas.addSpritesheet(16, this);

		for (int i = 0; i < itemSprites.size(); i++) {
			INDEXES.put(i, itemSprites.get(i).index);
		}
	}

	public int getTextureIndex(int index) {
		return INDEXES.getOrDefault(index, 0);
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
