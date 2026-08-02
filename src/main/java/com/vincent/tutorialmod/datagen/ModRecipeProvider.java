package com.vincent.tutorialmod.datagen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "TutorialMod Recipes";
        }
    }

    @Override
    public void buildRecipes() {
        registerBlockToItemRecipes(ModBlocks.AZURITE_BLOCK, ModItems.AZURITE, "azurite");
        registerBlockToItemRecipes(ModBlocks.RAW_AZURITE_BLOCK, ModItems.RAW_AZURITE, "raw_azurite");

        List<ItemLike> AZURITE_SMELTABLES = List.of(ModItems.RAW_AZURITE,
                ModBlocks.AZURITE_ORE, ModBlocks.AZURITE_DEEPSLATE_ORE,
                ModBlocks.AZURITE_END_ORE, ModBlocks.AZURITE_NETHER_ORE);

        oreSmelting(AZURITE_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.AZURITE.get(),
                0.25f, 200, "azurite");
        oreBlasting(AZURITE_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.AZURITE.get(),
                0.25f, 100, "azurite");

        oreSmelting(List.of(ModBlocks.RAW_AZURITE_BLOCK), RecipeCategory.MISC, CookingBookCategory.MISC, ModBlocks.AZURITE_BLOCK.get(),
                2.25f, 1800, "azurite");
        oreBlasting(List.of(ModBlocks.RAW_AZURITE_BLOCK), RecipeCategory.MISC, CookingBookCategory.MISC, ModBlocks.AZURITE_BLOCK.get(),
                2.25f, 900, "azurite");

        stairs(ModBlocks.AZURITE_STAIRS.get(), ModBlocks.AZURITE_BLOCK.get(), "azurite");
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AZURITE_SLAB.get(), ModBlocks.AZURITE_BLOCK.get());
    }

    protected void stairs(Block stairBlock, Block baseBlock, String groupName){
        stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void stairs(Block stairBlock, Block baseBlock){
        stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    @Override
    public <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, List<ItemLike> smeltables,
                                                                RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result,
                                                                float experience, int cookingTime, String group, String fromDesc) {
        for(ItemLike itemlike : smeltables) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), craftingCategory, cookingCategory, result, experience, cookingTime, factory).group(group).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(output, TutorialMod.MOD_ID + ":" + getItemName(result) + fromDesc + "_" + getItemName(itemlike));
        }
    }

    public void registerBlockToItemRecipes(DeferredBlock<Block> block, DeferredItem<Item> item, String groupName) {
        registerBlockToItemRecipes(block.get(), item.get(), groupName);
    }

    public void registerBlockToItemRecipes(Block block, Item item, String groupName) {
        shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', item)
                .unlockedBy("has_" + groupName, has(item))
                .group(groupName)
                .save(output);
        shapeless(RecipeCategory.MISC, item, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .group(groupName)
                .save(output);
    }
}
