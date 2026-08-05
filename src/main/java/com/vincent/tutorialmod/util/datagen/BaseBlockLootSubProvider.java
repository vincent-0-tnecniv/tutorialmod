package com.vincent.tutorialmod.util.datagen;

import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Set;

public abstract class BaseBlockLootSubProvider extends BlockLootSubProvider {
    protected BaseBlockLootSubProvider(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, HolderLookup.Provider registries) {
        super(explosionResistant, enabledFeatures, registries);
    }

    protected void addBerry(Block pBlock, Item item) {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        this.add(pBlock, block -> this.applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))).add(LootItem.lootTableItem(item)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))).add(LootItem.lootTableItem(item)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
    }

    protected void addBerry(DeferredBlock<Block> block, DeferredItem<Item> item) {
        addBerry(block.get(), item.get());
    }

    protected void addBerry(DeferredBlock<Block> block, Item item) {
        addBerry(block.get(), item);
    }

    protected void addBerry(Block block, DeferredItem<Item> item) {
        addBerry(block, item.get());
    }
}
