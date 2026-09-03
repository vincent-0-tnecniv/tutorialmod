package com.vincent.tutorialmod.datagen.tags;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TutorialMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.AZURITE_BLOCK.getKey())
                .add(ModBlocks.RAW_AZURITE_BLOCK.getKey())
                .add(ModBlocks.AZURITE_ORE.getKey())
                .add(ModBlocks.AZURITE_NETHER_ORE.getKey())
                .add(ModBlocks.AZURITE_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.AZURITE_END_ORE.getKey())
                .add(ModBlocks.MAGIC_BLOCK.getKey())
                .add(ModBlocks.AZURITE_STAIRS.getKey())
                .add(ModBlocks.AZURITE_SLAB.getKey())
                .add(ModBlocks.PEDESTAL_BLOCK.getKey())
                .add(ModBlocks.CRYSTALLIZER.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.AZURITE_DEEPSLATE_ORE.getKey());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.AZURITE_NETHER_ORE.getKey());

        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.AZURITE_END_ORE.getKey());

        tag(ModTags.Blocks.METAL_DETECTABLES)
                .addTag(Tags.Blocks.ORES)
                .add(ModBlocks.AZURITE_ORE.getKey())
                .add(ModBlocks.AZURITE_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.AZURITE_END_ORE.getKey())
                .add(ModBlocks.AZURITE_NETHER_ORE.getKey());

        // adding to non-block block sets is not STRICTLY necessary
        // however, still good to add as convention so that other mods, if needed,
        //  support the use of the non-block blocks
        tag(BlockTags.STAIRS)
                .add(ModBlocks.AZURITE_STAIRS.getKey());
        tag(BlockTags.SLABS)
                .add(ModBlocks.AZURITE_SLAB.getKey());
        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.AZURITE_PRESSURE_PLATE.getKey());
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.AZURITE_BUTTON.getKey());
        tag(BlockTags.DOORS)
                .add(ModBlocks.AZURITE_DOOR.getKey());
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.AZURITE_TRAPDOOR.getKey());

        // For a block family, these are MANDATORY
//        tag(BlockTags.WOODEN_FENCES)
//                .add(ModBlocks.AZURITE_FENCE));
        // For wooden fences, the above tag is needed as they don't connect to those that aren't
        tag(BlockTags.FENCES)
                .add(ModBlocks.AZURITE_FENCE.getKey());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.AZURITE_FENCE_GATE.getKey());
        tag(BlockTags.WALLS)
                .add(ModBlocks.AZURITE_WALL.getKey());

        tag(ModTags.Blocks.NEEDS_AZURITE_TOOL)
                .add(ModBlocks.MAGIC_BLOCK.getKey())
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_AZURITE_TOOL)
                .remove(ModBlocks.MAGIC_BLOCK.getKey())
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(BlockTags.CROPS)
                .add(ModBlocks.ONION_CROP.getKey());
        // make blocks placable on farmland
        
        tag(BlockTags.LEAVES)
                .add(ModBlocks.DRIFTWOOD_LEAVES.getKey());
        tag(BlockTags.PLANKS)
                .add(ModBlocks.DRIFTWOOD_PLANKS.getKey());
        tag(BlockItemTags.LOGS_THAT_BURN.block())
                .add(ModBlocks.DRIFTWOOD_LOG.getKey())
                .add(ModBlocks.STRIPPED_DRIFTWOOD_LOG.getKey())
                .add(ModBlocks.DRIFTWOOD_WOOD.getKey())
                .add(ModBlocks.STRIPPED_DRIFTWOOD_WOOD.getKey());
        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_DRIFTWOOD_SAPLING.getKey());
    }

    // Use these methods for any adding of vanilla blocks

    protected void addToTag(TagKey<Block> tag, Block block) {
        ResourceKey<Block> key = block.builtInRegistryHolder().getKey();
        if(key == null) {
            throw new NullPointerException(block.getDescriptionId() + " not found in registry");
        }
        tag(tag).add(key);
    }

    protected void addToTag(TagKey<Block> tag, List<Block> blocks) {
        for(Block block : blocks) {
            if(block.builtInRegistryHolder().getKey() == null) {
                throw new NullPointerException(block.getDescriptionId() + " not found in registry");
            }
            addToTag(tag, block);
        }
    }
}
