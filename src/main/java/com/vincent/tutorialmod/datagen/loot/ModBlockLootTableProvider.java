package com.vincent.tutorialmod.datagen.loot;

import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.block.custom.OnionCropBlock;
import com.vincent.tutorialmod.block.custom.RiceCropBlock;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.util.datagen.BaseBlockLootSubProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BaseBlockLootSubProvider {

    public ModBlockLootTableProvider(Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.AZURITE_BLOCK.get());
        dropSelf(ModBlocks.RAW_AZURITE_BLOCK.get());
        add(ModBlocks.AZURITE_ORE.get(), createOreDrop(ModBlocks.AZURITE_ORE.get(), ModItems.RAW_AZURITE.get()));
        add(ModBlocks.AZURITE_DEEPSLATE_ORE.get(), createOreDrop(ModBlocks.AZURITE_DEEPSLATE_ORE.get(), ModItems.RAW_AZURITE.get()));
        add(ModBlocks.AZURITE_NETHER_ORE.get(),
                createMultipleOreDrops(ModBlocks.AZURITE_NETHER_ORE.get(), ModItems.RAW_AZURITE.get(), 4, 7));
        add(ModBlocks.AZURITE_END_ORE.get(),
                createMultipleOreDrops(ModBlocks.AZURITE_NETHER_ORE.get(), ModItems.RAW_AZURITE.get(), 4, 7));

        dropSelf(ModBlocks.MAGIC_BLOCK.get());
        dropSelf(ModBlocks.AZURITE_STAIRS.get());

        add(ModBlocks.AZURITE_SLAB.get(), this::createSlabItemTable);

        dropSelf(ModBlocks.AZURITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.AZURITE_BUTTON.get());
        dropSelf(ModBlocks.AZURITE_FENCE.get());
        dropSelf(ModBlocks.AZURITE_FENCE_GATE.get());
        dropSelf(ModBlocks.AZURITE_WALL.get());
        dropSelf(ModBlocks.AZURITE_TRAPDOOR.get());

        add(ModBlocks.AZURITE_DOOR.get(), this::createDoorTable);

        dropSelf(ModBlocks.AZURITE_LAMP.get());
        dropSelf(ModBlocks.PEDESTAL_BLOCK.get());
        dropSelf(ModBlocks.CRYSTALLIZER.get());

        dropSelf(ModBlocks.DRIFTWOOD_LOG.get());
        dropSelf(ModBlocks.STRIPPED_DRIFTWOOD_LOG.get());
        dropSelf(ModBlocks.DRIFTWOOD_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_DRIFTWOOD_WOOD.get());
        dropSelf(ModBlocks.DRIFTWOOD_PLANKS.get());

        add(ModBlocks.DRIFTWOOD_LEAVES.get(), block -> createLeavesDrops(block, ModBlocks.PEDESTAL_BLOCK.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        addCrop(ModItems.ONION, ModItems.ONION_SEEDS, ModBlocks.ONION_CROP, OnionCropBlock.AGE, 3);

        addBerry(ModBlocks.GOJI_BERRY_BUSH, ModItems.GOJI_BERRIES);

        addCrop(ModItems.RICE_SHOOT, ModItems.RICE_SHOOT, ModBlocks.RICE_CROP, RiceCropBlock.AGE, 7);
    }

    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
