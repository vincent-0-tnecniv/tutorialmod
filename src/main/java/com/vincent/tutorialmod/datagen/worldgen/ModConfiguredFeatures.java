package com.vincent.tutorialmod.datagen.worldgen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AZURITE_ORE_KEY = registerKey("overworld_azurite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_AZURITE_ORE_KEY = registerKey("nether_azurite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_AZURITE_ORE_KEY = registerKey("end_azurite_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DRIFTWOOD_KEY = registerKey("driftwood");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GOJI_BERRY_BUSH_KEY = registerKey("goji_berry_bush");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
//        final RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
//        final RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        final RuleTest netherrackReplaceables = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
        final RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        registerSimpleOverworldOres(context, OVERWORLD_AZURITE_ORE_KEY, ModBlocks.AZURITE_ORE, ModBlocks.AZURITE_DEEPSLATE_ORE, 9);

        registerSimpleNonOverworldOres(context, NETHER_AZURITE_ORE_KEY, netherrackReplaceables,  ModBlocks.AZURITE_NETHER_ORE, 7);
        registerSimpleNonOverworldOres(context, END_AZURITE_ORE_KEY, endReplaceables,  ModBlocks.AZURITE_END_ORE, 12);

        register(context, DRIFTWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(
                        ModBlocks.DRIFTWOOD_LOG.get() // the LOG of the tree
                ),
                new ForkingTrunkPlacer(4, 3, 4),

                BlockStateProvider.simple(
                        ModBlocks.DRIFTWOOD_LEAVES.get() // the LEAVES of the tree
                ),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(3), 3),

                new TwoLayersFeatureSize(1, 0, 2),
                // the minimum bounding box for the sapling to grow as a tree
                BlockStateProvider.simple(
                        Blocks.DIRT
                        // this determines what the tree can SPAWN on
                        // this is not the same as "what the sapling can be PLACED on"!!
                )
        ).build());

        register(context, GOJI_BERRY_BUSH_KEY, Feature.SIMPLE_RANDOM_SELECTOR, new CompositeFeatureConfiguration(
                HolderSet.direct(PlacementUtils.inlinePlaced(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(
                                        ModBlocks.GOJI_BERRY_BUSH // the bush block
                                        .get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3)
                        )),
                        CountPlacement.of(32),
                        RandomOffsetPlacement.ofTriangle(6, 3),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.ONLY_IN_AIR_PREDICATE
                        )))));
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
