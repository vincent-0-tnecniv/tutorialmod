package com.vincent.tutorialmod;

import com.vincent.tutorialmod.datagen.*;
import com.vincent.tutorialmod.datagen.datapack.ModDataPackProvider;
import com.vincent.tutorialmod.datagen.datapack.paintings.ModPaintingTagsProvider;
import com.vincent.tutorialmod.datagen.datapack.sounds.ModSoundsProvider;
import com.vincent.tutorialmod.datagen.loot.ModBlockLootTableProvider;
import com.vincent.tutorialmod.datagen.loot.ModExtraLootProvider;
import com.vincent.tutorialmod.datagen.loot.ModGlobalLootTableModifierProvider;
import com.vincent.tutorialmod.datagen.recipe.ModRecipeProvider;
import com.vincent.tutorialmod.datagen.tags.*;
import com.vincent.tutorialmod.datagen.villager.tags.ModPOITags;
import com.vincent.tutorialmod.datagen.villager.tags.ModVillagerTradeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class TutorialModDataGen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModModelProvider(packOutput));
        generator.addProvider(true, new ModBlockTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModItemTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ModExtraLootProvider::new, LootContextParamSets.ALL_PARAMS)
                ), lookupProvider));
        generator.addProvider(true, new ModRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new ModDataMapProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModEquipmentAssetProvider(packOutput));
        generator.addProvider(true, new ModDataPackProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModPaintingTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModSoundsProvider(packOutput));
        generator.addProvider(true, new ModDamageTypeTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModAdvancements(packOutput, lookupProvider));
        generator.addProvider(true, new ModVillagerTradeTags(packOutput, lookupProvider));
        generator.addProvider(true, new ModPOITags(packOutput, lookupProvider));
        generator.addProvider(true, new ModGlobalLootTableModifierProvider(packOutput, lookupProvider));
    }
}
