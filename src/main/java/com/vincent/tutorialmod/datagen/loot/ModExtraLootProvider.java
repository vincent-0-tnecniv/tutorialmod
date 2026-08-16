package com.vincent.tutorialmod.datagen.loot;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiConsumer;

public class ModExtraLootProvider implements LootTableSubProvider {

    public static final ResourceKey<LootTable> ONION_SEEDS = createKey(ModItems.ONION_SEEDS);
    public static final ResourceKey<LootTable> METAL_DETECTOR_FOUND = createKey("metal_detector_found");
    public static final ResourceKey<LootTable> RAW_AZURITE = createKey(ModItems.RAW_AZURITE.get());

    public ModExtraLootProvider(HolderLookup.Provider provider) {

    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        // This generates an object that tells:
        // 1. What to be added?
        // 2. How much to be added?
        // 3. Where to be added?
        // 4. How much chance for it to proc?

        output.accept(ONION_SEEDS, simpleLoot(1, 0.25f, ModItems.ONION_SEEDS));
        output.accept(METAL_DETECTOR_FOUND, simpleLoot(1, 1f, ModItems.METAL_DETECTOR, 1, 2));
        output.accept(RAW_AZURITE, simpleLoot(1, 0.5f, ModItems.RAW_AZURITE, 2, 4));
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, Item itemDropped, int minAmount, int maxAmount) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(rolls))
                        .when(LootItemRandomChanceCondition.randomChance(randomChance))
                        .add(LootItem.lootTableItem(itemDropped))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minAmount, maxAmount)))
        );
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, Item itemDropped) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(rolls))
                        .when(LootItemRandomChanceCondition.randomChance(randomChance))
                        .add(LootItem.lootTableItem(itemDropped))
        );
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, DeferredItem<Item> itemDropped, int minAmount, int maxAmount) {
        return simpleLoot(rolls, randomChance, itemDropped.get(), minAmount, maxAmount);
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, DeferredItem<Item> itemDropped) {
        return simpleLoot(rolls, randomChance, itemDropped.get());
    }

    protected static ResourceKey<LootTable> createKey(Item item) {
        return createKey(BuiltInRegistries.ITEM.getKey(item).getPath());
    }

    protected static ResourceKey<LootTable> createKey(DeferredItem<Item> item) {
        return createKey(item.get());
    }

    protected static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "extra/glm/" + id));
    }

}

