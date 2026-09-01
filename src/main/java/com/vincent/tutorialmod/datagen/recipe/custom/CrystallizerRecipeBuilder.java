package com.vincent.tutorialmod.datagen.recipe.custom;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.recipe.custom.CrystallizerRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

public class CrystallizerRecipeBuilder implements RecipeBuilder {
    // The following are generally needed for any data-gen recipes
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private CrystallizerRecipeBuilder(RecipeCategory category, Ingredient ingredient, ItemStackTemplate result) {
        this.category = category;
        this.result = result;
        this.ingredient = ingredient;
    }

    public static CrystallizerRecipeBuilder create(RecipeCategory category, Ingredient ingredient, ItemLike result, int count) {
        return new CrystallizerRecipeBuilder(category, ingredient, new ItemStackTemplate(result.asItem(), count));
    }

    public static CrystallizerRecipeBuilder create(RecipeCategory category, Ingredient ingredient, ItemLike result) {
        return new CrystallizerRecipeBuilder(category, ingredient, new ItemStackTemplate(result.asItem()));
    }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(s, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        this.group = s;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        // For any one-input-one-output recipe, it should look somemthing like this
        CrystallizerRecipe recipe = new CrystallizerRecipe(this.ingredient, this.result);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }

    @Override
    public void save(RecipeOutput output) {
        this.save(output, TutorialMod.MOD_ID + ":" + getItemName(this.result) + "_from_crystallizing_" + getFirstIndexItemName(this.ingredient));
        // There should be only one IO -
    }

    private String getFirstIndexItemName(Ingredient item) {
        return item.getValues().get(0).getRegisteredName().split(":")[1];
    }

    private String getItemName(ItemStackTemplate item) {
        return item.item().getRegisteredName().split(":")[1];
    }
}
