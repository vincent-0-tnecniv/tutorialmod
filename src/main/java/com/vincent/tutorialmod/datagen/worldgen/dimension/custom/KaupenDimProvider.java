package com.vincent.tutorialmod.datagen.worldgen.dimension.custom;

import com.mojang.datafixers.util.Pair;
import com.vincent.tutorialmod.datagen.worldgen.dimension.ModDimensions;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.timeline.Timeline;

import java.util.List;
import java.util.Optional;

public class KaupenDimProvider {

    public static void registerKaupenDimType(BootstrapContext<DimensionType> context, HolderGetter<Timeline> timelines, HolderGetter<WorldClock> clocks, HolderGetter<Block> blocks) {
        context.register(ModDimensions.KAUPEN_DIM_TYPE_KEY, new DimensionType(
                // has day-night cycle?
                false,
                // has light from the sky?
                true,
                // has a ceiling (like the nether)?
                false,
                // has an Ender Dragon fight?
                false,
                // the ratio of coordinate scaling (e.g. 1:8 for nether:overworld)?
                1.0,
                // minimum Y-level of the Level?
                0,
                256,
                256,
                // blocks that burn infinitely in the dimension
                blocks.getOrThrow(BlockTags.INFINIBURN_OVERWORLD),
                1.0f,
                new DimensionType.MonsterSettings(ConstantInt.of(0), 0),
                DimensionType.Skybox.OVERWORLD,
                CardinalLighting.Type.DEFAULT,
                // environment attributes
                // there are a lot of customizations here!
                EnvironmentAttributeMap.builder()
                        .set(EnvironmentAttributes.FOG_COLOR, -6168523)
                        .set(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(2.5f))
                        .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, -4212331)
                        .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.color(155, 200, 31, 25))
                        .build(),
                timelines.getOrThrow(TimelineTags.IN_OVERWORLD),
                // how time flows
                Optional.of(clocks.getOrThrow(WorldClocks.OVERWORLD))));
    }

    public static void registerKaupenDimLevelStem(BootstrapContext<LevelStem> context, HolderGetter<Biome> biomes, HolderGetter<DimensionType> dimensionTypes, HolderGetter<NoiseGeneratorSettings> noiseGenSettings) {
        NoiseBasedChunkGenerator singleBiomeGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomes.getOrThrow(Biomes.CHERRY_GROVE)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        NoiseBasedChunkGenerator multiBiomeGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(0f, 0f, 0f, 0f, 0f, 0f, 0f), biomes.getOrThrow(Biomes.FOREST)),
                                Pair.of(Climate.parameters(0f, 0.1f, 0f, 0f, 0f, 0f, 0f), biomes.getOrThrow(Biomes.BIRCH_FOREST)),
                                Pair.of(Climate.parameters(0.1f, 0.1f, 0f, 0f, 0f, 0f, 0f), biomes.getOrThrow(Biomes.CHERRY_GROVE)),
                                Pair.of(Climate.parameters(0.1f, 0.25f, 0f, 0f, 0f, 0f, 0f), biomes.getOrThrow(Biomes.BEACH)),
                                Pair.of(Climate.parameters(0.1f, 0.3f, -0.05f, 0f, 0f, 0f, 0f), biomes.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN))
                                // This thing is quite confusing, so use this website to visualize how it would look!
                                // https://misode.github.io/dimension/
                        ))),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        context.register(ModDimensions.KAUPENDIM_KEY, new LevelStem(dimensionTypes.getOrThrow(ModDimensions.KAUPEN_DIM_TYPE_KEY), multiBiomeGenerator));
    }
}
