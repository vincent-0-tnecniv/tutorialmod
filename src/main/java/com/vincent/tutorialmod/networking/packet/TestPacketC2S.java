package com.vincent.tutorialmod.networking.packet;

import com.mojang.serialization.Codec;
import com.vincent.tutorialmod.TutorialMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record TestPacketC2S(String name, int value) implements CustomPacketPayload {

    public static final Type<TestPacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "test_packet"));

    // Codec: JSON files (read N write)
    // Does:
    // 1. Turn Java Classes to JSON files
    // 2. Create Java Instances using JSON files

    // StreamCodec: Networking
    // Turns Java Classes to bits for the network
    // Turns bits from the network to Java Instances

    public static final StreamCodec<RegistryFriendlyByteBuf, TestPacketC2S> STREAM_CODEC =
            StreamCodec.composite(
                    // This is what the DATA TYPE of the packet would be
                    // This tells the codec how to convert it to a ByteBuf

                    // For primitive data type (e.g. integer ) codecs, using ByteBufCodecs

                    ByteBufCodecs.STRING_UTF8,
                    TestPacketC2S::name,

                    // What does that mean?
                    // This tells the codec: the name parameter of a TestPacketC2S is a String!
                    // Note that this always comes as a pair!

                    ByteBufCodecs.VAR_INT,
                    TestPacketC2S::value,

                    // For custom objects, in vanilla, there would usually be a codec of that object

                    TestPacketC2S::new
                    // Making a codec always ends with a constructor of the packet
            );

    // Any StreamCodec has two types, e.g. StreamCodec<T, U>
    // T is the ByteBuf
    // In Minecraft - a registry-oriented environment - can use RegistryFriendlyByteBuf
    // U is the class to be turned into ByteBuf and to be created using ByteBuf

    // In other words,
    // Object U -> Object T (receiver of packets)
    // Object T -> Object U (sender of packets)

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
