package com.vincent.tutorialmod.datagen.worldgen;

import com.vincent.tutorialmod.TutorialMod;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_OVERWORLD_AZURITE_ORE = registerKey("add_overworld_azurite_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_AZURITE_ORE = registerKey("add_nether_azurite_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_AZURITE_ORE = registerKey("add_end_azurite_ore");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        final var PF = context.lookup(Registries.PLACED_FEATURE);
        final var BIOMES = context.lookup(Registries.BIOME);

        context.register(ADD_OVERWORLD_AZURITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                BIOMES.getOrThrow(
                        BiomeTags.IS_OVERWORLD // Which biomes / biome tags are added
                        // For multiple biomes, adding to a tag key OR using varargs of the HolderSet
                        // can both work

                        // For biomes + tags, adding to a tag key is generally recommended
                ),
                HolderSet.direct(PF.getOrThrow(
                        ModPlacedFeatures.OVERWORLD_AZURITE_ORE_PLACED_KEY
                        // the PF key
                )),
                GenerationStep.Decoration.UNDERGROUND_ORES
                // How is the feature generated
        ));

        context.register(ADD_NETHER_AZURITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                BIOMES.getOrThrow(
                        BiomeTags.IS_NETHER
                ),
                HolderSet.direct(PF.getOrThrow(
                        ModPlacedFeatures.NETHER_AZURITE_ORE_PLACED_KEY
                )),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_END_AZURITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                BIOMES.getOrThrow(
                        BiomeTags.IS_END
                ),
                HolderSet.direct(PF.getOrThrow(
                        ModPlacedFeatures.END_AZURITE_ORE_PLACED_KEY
                )),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
    }
}
