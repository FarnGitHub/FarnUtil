package farn.farn_util.mixin.item_usage.server;

import farn.farn_util.FarnUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ServerPlayerEntity.class, priority = 900)
public abstract class PlayerMPMixin extends PlayerEntity {
    public PlayerMPMixin(MinecraftServer server, World world, String name, ServerPlayerInteractionManager interactionManager) {
        super(world);
    }

    @Override
    public void farnutil_finishUsingItem() {
        PacketHelper.sendTo(this, new MessagePacket(FarnUtil.NAMESPACE.id("item_usage_api_finished")));
        super.farnutil_finishUsingItem();
    }
}
