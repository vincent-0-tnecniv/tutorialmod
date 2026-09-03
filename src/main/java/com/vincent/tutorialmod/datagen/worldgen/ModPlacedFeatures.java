package com.vincent.tutorialmod.datagen.worldgen;

import com.vincent.tutorialmod.TutorialMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> OVERWORLD_AZURITE_ORE_PLACED_KEY =
            registerKey("overworld_azurite_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_AZURITE_ORE_PLACED_KEY =
            registerKey("nether_azurite_ore_placed");
    public static final ResourceKey<PlacedFeature> END_AZURITE_ORE_PLACED_KEY =
            registerKey("end_azurite_ore_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        final var CF = context.lookup(Registries.CONFIGURED_FEATURE);
        // This creates an association with configured features and their keys

        // Usually, for each placement, each thing points to something in the JSON file
        // For the actual "pointing", refer to the comments below
        register(context, OVERWORLD_AZURITE_ORE_PLACED_KEY, CF.getOrThrow(ModConfiguredFeatures.OVERWORLD_AZURITE_ORE_KEY),
                OrePlacements.commonOrePlacement(
                        12 // This is how many ores would be placed in one chunk
                        , HeightRangePlacement.triangle( // This is the placement pattern of the ore
                                // i.e. the ore would place in a way that is less common when higher in Y,
                                // and more common when lower in Y
                                VerticalAnchor.absolute(
                        -64 // This is the minimum Y-level for the ore to generate
                ), VerticalAnchor.absolute(
                        80 // This is the maximum Y-level for the ore to generate
                ))));

        register(context, NETHER_AZURITE_ORE_PLACED_KEY, CF.getOrThrow(ModConfiguredFeatures.NETHER_AZURITE_ORE_KEY),
                OrePlacements.commonOrePlacement(
                        12
                        , HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(
                        -64
                ), VerticalAnchor.absolute(
                        80
                ))));

        register(context, END_AZURITE_ORE_PLACED_KEY, CF.getOrThrow(ModConfiguredFeatures.END_AZURITE_ORE_KEY),
                OrePlacements.commonOrePlacement(
                        12
                        , HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(
                        -64
                ), VerticalAnchor.absolute(
                        80
                ))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
