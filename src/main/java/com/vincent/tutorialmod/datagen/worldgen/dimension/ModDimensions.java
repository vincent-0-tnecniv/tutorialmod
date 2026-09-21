package com.vincent.tutorialmod.datagen.worldgen.dimension;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.datagen.worldgen.dimension.custom.KaupenDimProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class ModDimensions {
    public static final ResourceKey<LevelStem> KAUPENDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupendim"));
    public static final ResourceKey<Level> KAUPENDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupendim"));
    public static final ResourceKey<DimensionType> KAUPEN_DIM_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,
            Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "kaupendim_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        var timelines = context.lookup(Registries.TIMELINE);
        var clocks = context.lookup(Registries.WORLD_CLOCK);
        var blocks = context.lookup(Registries.BLOCK);

        KaupenDimProvider.registerKaupenDimType(context, timelines, clocks, blocks);
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        var biomes = context.lookup(Registries.BIOME);
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        var noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        KaupenDimProvider.registerKaupenDimLevelStem(context, biomes, dimensionTypes, noiseGenSettings);
    }

}
