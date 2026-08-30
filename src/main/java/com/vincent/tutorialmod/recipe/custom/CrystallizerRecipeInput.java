package com.vincent.tutorialmod.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CrystallizerRecipeInput(ItemStack input) implements RecipeInput {
    // gives the recipe
    @Override
    public ItemStack getItem(int i) {
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
