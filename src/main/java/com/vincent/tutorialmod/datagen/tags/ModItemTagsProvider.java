package com.vincent.tutorialmod.datagen.tags;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
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
    }

    protected void addToTag(TagKey<Item> tag, Item item) {
        ResourceKey<Item> key = item.builtInRegistryHolder().getKey();
        if(key == null) {
            throw new NullPointerException(item.getDescriptionId() + " not found in registry");
        }
        tag(tag).add(key);
    }

    protected void addToTag(TagKey<Item> tag, List<Item> items) {
        for(Item item : items) {
            if(item.builtInRegistryHolder().getKey() == null) {
                throw new NullPointerException(item.getDescriptionId() + " not found in registry");
            }
            addToTag(tag, item);
        }
    }

}
