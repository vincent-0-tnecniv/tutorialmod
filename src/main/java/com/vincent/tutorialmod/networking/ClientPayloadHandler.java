package com.vincent.tutorialmod.networking;

import com.vincent.tutorialmod.networking.packet.TestPacketC2S;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// CLIENT PACKETS TO SERVER
public class ClientPayloadHandler {
    // ON SIDE: SERVER

    public static void handleTestPacket(TestPacketC2S testPacketC2S, IPayloadContext context) {
        EntityTypes.COW.spawn((ServerLevel) context.player().level(), context.player().getOnPos(), EntitySpawnReason.TRIGGERED);
    }
}
