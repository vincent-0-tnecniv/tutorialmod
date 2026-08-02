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
                .add(ModBlocks.AZURITE_BLOCK.getKey())
                .add(ModBlocks.RAW_AZURITE_BLOCK.getKey())
                .add(ModBlocks.AZURITE_ORE.getKey())
                .add(ModBlocks.AZURITE_NETHER_ORE.getKey())
                .add(ModBlocks.AZURITE_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.AZURITE_END_ORE.getKey())
                .add(ModBlocks.MAGIC_BLOCK.getKey());
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
