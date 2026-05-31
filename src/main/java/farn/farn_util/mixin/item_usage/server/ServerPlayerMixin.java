package farn.farn_util.mixin.item_usage.server;

import farn.farn_util.FarnUtil;
import farn.farn_util.mixin.item_usage.common.PlayerMixin;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ServerPlayerEntity.class, priority = 900)
public class ServerPlayerMixin extends PlayerMixin {

    @Override
    public void farnutil_finishUsingItem() {
        PacketHelper.sendTo(farnutil_self(), new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_finished")));
        super.farnutil_finishUsingItem();
    }
}
