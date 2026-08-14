package farn.farn_util.mixin.custom_atlas;

import farn.farn_util.api.custom_atlas.CustomAtlas;
import net.modificationstation.stationapi.api.client.texture.atlas.AtlasLoader;
import net.modificationstation.stationapi.api.client.texture.atlas.AtlasSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AtlasLoader.class)
public class AtlasLoaderMixin {

    @Shadow
    @Final
    private List<AtlasSource> sources;

    @Inject(method="<init>", at = @At("TAIL"))
    public void farnutil_addAllAtlas(CallbackInfo ci) {
        this.sources.addAll(CustomAtlas.getAtlasSources());
    }
}
