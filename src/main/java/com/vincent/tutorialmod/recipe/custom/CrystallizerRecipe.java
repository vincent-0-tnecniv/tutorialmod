package com.vincent.tutorialmod.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.vincent.tutorialmod.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.level.Level;

public record CrystallizerRecipe(Ingredient inputItem, ItemStackTemplate output) implements Recipe<CrystallizerRecipeInput> {

    public static final MapCodec<CrystallizerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    // To put in simply,
                    // use (some codec).fieldOf("nameOfTheField").forGetter(the actual object)
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(CrystallizerRecipe::inputItem),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(CrystallizerRecipe::output)
            ).apply(instance, CrystallizerRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CrystallizerRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    CrystallizerRecipe::inputItem,

                    ItemStackTemplate.STREAM_CODEC,
                    CrystallizerRecipe::output,

                    CrystallizerRecipe::new
            );

    @Override
    public boolean matches(CrystallizerRecipeInput crystallizerRecipeInput, Level level) {
        if(level.isClientSide()) return false;
        return inputItem.test(crystallizerRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(CrystallizerRecipeInput crystallizerRecipeInput) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Crystallizing";
    }

    @Override
    public RecipeSerializer<? extends Recipe<CrystallizerRecipeInput>> getSerializer() {
        return ModRecipes.CRYSTALLIZER_ST_PAIR.SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<CrystallizerRecipeInput>> getType() {
        return ModRecipes.CRYSTALLIZER_ST_PAIR.TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
