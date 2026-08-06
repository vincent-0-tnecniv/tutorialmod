package com.vincent.tutorialmod.datagen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger.TriggerInstance;

public class ModAdvancements extends AdvancementProvider {
    public ModAdvancements(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, List.of(new TutorialModAdvancements()));
    }

    public static class TutorialModAdvancements implements AdvancementSubProvider {

        @Override
        public void generate(Provider provider, Consumer<AdvancementHolder> output) {
            RegistryLookup<Item> items = provider.lookupOrThrow(Registries.ITEM);

            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            ModItems.AZURITE,
                            Component.translatable("advancements.tutorialmod.root.title"),
                            Component.translatable("advancements.tutorialmod.root.description"),
                            Identifier.withDefaultNamespace("gui/advancements/backgrounds/adventure"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                            )
                    .addCriterion("has_azurite", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, ModItems.AZURITE.get())))
                    .save(output, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "tutorialmod/root"));

            AdvancementHolder plantSeed = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModItems.RICE_SHOOT,
                            Component.translatable("advancements.tutorialmod.plant_custom.title"),
                            Component.translatable("advancements.tutorialmod.plant_custom.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("berries", TriggerInstance.placedBlock(ModBlocks.GOJI_BERRY_BUSH.get()))
                    .addCriterion("rice", TriggerInstance.placedBlock(ModBlocks.RICE_CROP.get()))
                    .addCriterion("onion", TriggerInstance.placedBlock(ModBlocks.ONION_CROP.get()))
                    .save(output, "tutorialmod/plant_custom");

            AdvancementHolder metalDetector = Advancement.Builder.advancement()
                    .parent(plantSeed)
                    .display(
                            ModItems.METAL_DETECTOR,
                            Component.translatable("advancements.tutorialmod.metal_detector.title"),
                            Component.translatable("advancements.tutorialmod.metal_detector.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("metal_detector", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setCanSeeSky(true),
                            ItemPredicate.Builder.item().of(items, ModItems.METAL_DETECTOR.asItem())))
                    .save(output, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "tutorialmod/metal_detector"));
        }
    }

    // however many nested classes there are here, however many advancement TABS are created
}
