package farn.farn_util.mixin.dungeon.apron;

import farn.farn_util.FarnUtil;
import farn.farn_util.api.dungeon.event.DungeonDefaultLootEvent;
import farn.farn_util.api.dungeon.event.DungeonDefaultMobEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shockahpi.SAPI;

@Pseudo
@Mixin(SAPI.class)
public class SAPIMixin {

    @Inject(method="dungeonAddDefaultItems", at = @At("TAIL"))
    private static void farnutil_addDefaultLoot(CallbackInfo ci) {
        FarnUtil.setupEvent(new DungeonDefaultLootEvent());
    }

    @Inject(method="dungeonAddDefaultMobs", at = @At("TAIL"))
    private static void farnutil_addDefaultMob(CallbackInfo ci) {
        FarnUtil.setupEvent(new DungeonDefaultMobEvent());
    }

}
