package com.vincent.tutorialmod.datagen.worldgen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AZURITE_ORE_KEY = registerKey("overworld_azurite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_AZURITE_ORE_KEY = registerKey("nether_azurite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_AZURITE_ORE_KEY = registerKey("end_azurite_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
//        final RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
//        final RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        final RuleTest netherrackReplaceables = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
        final RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        registerSimpleOverworldOres(context, OVERWORLD_AZURITE_ORE_KEY, ModBlocks.AZURITE_ORE, ModBlocks.AZURITE_DEEPSLATE_ORE, 9);

        registerSimpleNonOverworldOres(context, NETHER_AZURITE_ORE_KEY, netherrackReplaceables,  ModBlocks.AZURITE_NETHER_ORE, 7);
        registerSimpleNonOverworldOres(context, END_AZURITE_ORE_KEY, endReplaceables,  ModBlocks.AZURITE_END_ORE, 12);
    }

    private static void registerSimpleOverworldOres(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> oreKey,
                                                    Block stoneOreBlock, Block deepslateOreBlock, int size) {
        final RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        final RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        register(context, oreKey, Feature.ORE, new OreConfiguration(
                List.of(
                        OreConfiguration.target(stoneReplaceables, stoneOreBlock.defaultBlockState()),
                        OreConfiguration.target(deepslateReplaceables, deepslateOreBlock.defaultBlockState())
                ), size
        ));
    }

    private static void registerSimpleOverworldOres(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> oreKey,
                                                    DeferredBlock<Block> stoneOreBlock, DeferredBlock<Block> deepslateOreBlock, int size) {
        registerSimpleOverworldOres(context, oreKey, stoneOreBlock.get(), deepslateOreBlock.get(), size);
    }

    private static void registerSimpleNonOverworldOres(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> oreKey,
                                                       RuleTest condition, Block oreBlock, int size) {
        register(context, oreKey, Feature.ORE, new OreConfiguration(
                condition, oreBlock.defaultBlockState(), size
        ));
    }

    private static void registerSimpleNonOverworldOres(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> oreKey,
                                                       RuleTest condition, DeferredBlock<Block> oreBlock, int size) {
        registerSimpleNonOverworldOres(context, oreKey, condition, oreBlock.get(), size);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
