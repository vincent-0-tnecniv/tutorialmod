package com.vincent.tutorialmod.datagen.tags;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
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
                .add(ModBlocks.getRK(ModBlocks.AZURITE_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.RAW_AZURITE_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_ORE))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_NETHER_ORE))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_DEEPSLATE_ORE))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_END_ORE))
                .add(ModBlocks.getRK(ModBlocks.MAGIC_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_STAIRS))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_SLAB))
                .add(ModBlocks.getRK(ModBlocks.PEDESTAL_BLOCK));

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_DEEPSLATE_ORE));

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_NETHER_ORE));

        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_END_ORE));

        tag(ModTags.Blocks.METAL_DETECTABLES)
                .addTag(Tags.Blocks.ORES)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_ORE))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_DEEPSLATE_ORE))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_END_ORE))
                .add(ModBlocks.getRK(ModBlocks.AZURITE_NETHER_ORE));

        // adding to non-block block sets is not STRICTLY necessary
        // however, still good to add as convention so that other mods, if needed,
        //  support the use of the non-block blocks
        tag(BlockTags.STAIRS)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_STAIRS));
        tag(BlockTags.SLABS)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_SLAB));
        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_PRESSURE_PLATE));
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_BUTTON));
        tag(BlockTags.DOORS)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_DOOR));
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_TRAPDOOR));

        // For a block family, these are MANDATORY
//        tag(BlockTags.WOODEN_FENCES)
//                .add(ModBlocks.getRK(ModBlocks.AZURITE_FENCE));
        // For wooden fences, the above tag is needed as they don't connect to those that aren't
        tag(BlockTags.FENCES)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_FENCE));
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_FENCE_GATE));
        tag(BlockTags.WALLS)
                .add(ModBlocks.getRK(ModBlocks.AZURITE_WALL));

        tag(ModTags.Blocks.NEEDS_AZURITE_TOOL)
                .add(ModBlocks.getRK(ModBlocks.MAGIC_BLOCK))
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_AZURITE_TOOL)
                .remove(ModBlocks.getRK(ModBlocks.MAGIC_BLOCK))
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(BlockTags.CROPS)
                .add(ModBlocks.getRK(ModBlocks.ONION_CROP));
        // make blocks placable on farmland
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
