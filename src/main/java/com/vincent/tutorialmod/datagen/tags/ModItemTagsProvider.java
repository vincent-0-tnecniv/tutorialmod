package com.vincent.tutorialmod.datagen.tags;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.tags.ModTags;
import com.vincent.tutorialmod.util.datagen.BaseItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends BaseItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TutorialMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        addToTag(ModTags.Items.TRANSFORMABLE_ITEMS,
            List.of(
                Items.IRON_INGOT,
                Items.REDSTONE,
                Items.COPPER_INGOT
            ));
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.AZURITE.getKey());
        tag(ModTags.Items.AZURITE_REPAIRABLE)
                .add(ModItems.AZURITE.getKey());

        allTools(ModItems.AZURITE_SWORD, ModItems.AZURITE_PICKAXE, ModItems.AZURITE_AXE,
                ModItems.AZURITE_SHOVEL, ModItems.AZURITE_HOE, ModItems.AZURITE_SPEAR);

        allArmor(ModItems.AZURITE_HELMET, ModItems.AZURITE_CHESTPLATE,
                ModItems.AZURITE_LEGGINGS, ModItems.AZURITE_BOOTS, true);

        tag(ItemTags.BOW_ENCHANTABLE)
                .add(ModItems.KAUPEN_BOW.getKey());

        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ModItems.BAR_BRAWL_MUSIC_DISC.getKey());

        tag(ItemTags.PLANKS)
                .add(ModItems.getRK(ModBlocks.DRIFTWOOD_PLANKS.asItem()));
        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModItems.getRK(ModBlocks.DRIFTWOOD_LOG.asItem()))
                .add(ModItems.getRK(ModBlocks.DRIFTWOOD_WOOD.asItem()))
                .add(ModItems.getRK(ModBlocks.STRIPPED_DRIFTWOOD_LOG.asItem()))
                .add(ModItems.getRK(ModBlocks.STRIPPED_DRIFTWOOD_WOOD.asItem()));

        tag(ModTags.Items.DRIFTWOOD_LOGS)
                .add(ModItems.getRK(ModBlocks.DRIFTWOOD_LOG.asItem()))
                .add(ModItems.getRK(ModBlocks.STRIPPED_DRIFTWOOD_LOG.asItem()))
                .add(ModItems.getRK(ModBlocks.DRIFTWOOD_WOOD.asItem()))
                .add(ModItems.getRK(ModBlocks.STRIPPED_DRIFTWOOD_WOOD.asItem()));
    }
}
