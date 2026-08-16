package com.vincent.tutorialmod.util.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BaseGlobalLootModifierProvider extends GlobalLootModifierProvider {


    public BaseGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(output, registries, modid);
    }

    protected AddTableLootModifier customLoot(LootItemCondition[] list, ResourceKey<LootTable> modifiedLootTable) {
        return new AddTableLootModifier(list, 1000, modifiedLootTable);
    }

    // TODO: add archaeology, brush, carve, charged_creeper, dispensers, equipment, gameplay, harvest, pots, shearing, spawners

    protected AddTableLootModifier simpleEntityLoot(List<EntityType<?>> entities, ResourceKey<LootTable> modifiedLootTable) {
        return new AddTableLootModifier(createEntityDropConditions(entities), 1000, modifiedLootTable);
    }

    private LootItemCondition[] createStructureConditions(List<ResourceKey<Structure>> structures) {
        LootItemCondition[] result = new LootItemCondition[structures.size()];
        for(int i = 0; i < structures.size(); i++) {
            if(structures.get(i).identifier().getPath().equals("jungle_pyramid")) {
                // There is a weird error with jungle temple - or what the game ID calls as a "jungle pyramid"
                result[i] = new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/jungle_temple")).build();
                continue;
            }
            result[i] = new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/" + structures.get(i).identifier().getPath())).build();
        }
        return result;
    }

    protected AddTableLootModifier simpleChestLoot(List<ResourceKey<Structure>> structures, ResourceKey<LootTable> modifiedLootTable) {
        return new AddTableLootModifier(createStructureConditions(structures), 1000, modifiedLootTable);
    }

    private LootItemCondition[] createEntityDropConditions(List<EntityType<?>> entities) {
        LootItemCondition[] result = new LootItemCondition[entities.size()];
        for(int i = 0; i < entities.size(); i++) {
            Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(entities.get(i));
            result[i] = new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("entities/" + id.getPath())).build();
        }
        return result;
    }

    protected AddTableLootModifier simpleBlockDrop(List<Block> blocks, ResourceKey<LootTable> modifiedLootTable) {
        // Assume that blocks is either a List<Block> or List<DeferredBlock<Block>>
        return new AddTableLootModifier(createBlockConditions(blocks), 1000, modifiedLootTable);
    }

    private LootItemCondition[] createBlockConditions(List<Block> blocks) {
        return new LootItemCondition[] {AnyOfCondition.anyOf(blocks.stream().map(LootItemBlockStatePropertyCondition::hasBlockStateProperties).toArray(LootItemBlockStatePropertyCondition.Builder[]::new)).build()};
    }

    protected abstract void start();
}
