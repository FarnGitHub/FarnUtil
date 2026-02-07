package farn.farn_util.mixin.item_usage;

import farn.farn_util.impl.item_usage.ItemUsageAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerMPMixin extends PlayerEntity {
    public PlayerMPMixin(MinecraftServer server, World world, String name, ServerPlayerInteractionManager interactionManager) {
        super(world);
    }

    @Override
    public void UseApi_finishedUsingItem() {
        PacketHelper.sendTo(this, new MessagePacket(ItemUsageAPI.NAMESPACE.id("item_usage_api_finished")));
        super.UseApi_finishedUsingItem();
    }
}
