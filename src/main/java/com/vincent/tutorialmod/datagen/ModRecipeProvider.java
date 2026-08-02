package com.vincent.tutorialmod.datagen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.util.BaseRecipeProvider;
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

public class ModRecipeProvider extends BaseRecipeProvider {
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

        stairs(ModBlocks.AZURITE_STAIRS, ModBlocks.AZURITE_BLOCK, "azurite");
        slab(ModBlocks.AZURITE_SLAB, ModBlocks.AZURITE_BLOCK, "azurite");
        button(ModBlocks.AZURITE_BUTTON, ModBlocks.AZURITE_BLOCK, "azurite");
        pressurePlate(ModBlocks.AZURITE_PRESSURE_PLATE, ModBlocks.AZURITE_BLOCK, "azurite");
        fence(ModBlocks.AZURITE_FENCE, ModBlocks.AZURITE_BLOCK, "azurite");
        fenceGate(ModBlocks.AZURITE_FENCE_GATE, ModBlocks.AZURITE_BLOCK, "azurite");
        wall(ModBlocks.AZURITE_WALL, ModBlocks.AZURITE_BLOCK,  "azurite");
        door(ModBlocks.AZURITE_DOOR, ModBlocks.AZURITE_BLOCK, "azurite");
        trapdoor(ModBlocks.AZURITE_TRAPDOOR, ModBlocks.AZURITE_BLOCK, "azurite");
    }
}
