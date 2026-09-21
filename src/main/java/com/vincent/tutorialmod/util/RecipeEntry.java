/// A class used to enhance readability and reusability for custom mod recipes.

package com.vincent.tutorialmod.util;

import com.mojang.serialization.MapCodec;
import com.vincent.tutorialmod.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public record RecipeEntry<RECIPE_INPUT extends RecipeInput, RECIPE extends Recipe<RECIPE_INPUT>>(
        DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RECIPE>> SERIALIZER,
        DeferredHolder<RecipeType<?>, RecipeType<RECIPE>> TYPE) {

    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            ModRecipes.SERIALIZERS;
    private static final DeferredRegister<RecipeType<?>> TYPES =
            ModRecipes.TYPES;

    // Java, by default, creating object REFERENCES!!
    // Hence, this does not create a new object - it just uses the object from ModRecipes
    // to process the rest!

    public static <INPUT extends RecipeInput, T extends Recipe<INPUT>> RecipeEntry<INPUT, T> create(String recipeName, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        return new RecipeEntry<>(SERIALIZERS.register(recipeName, () -> new RecipeSerializer<>(codec, streamCodec)), TYPES.register(recipeName, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return recipeName;
            }
        }));
    }

}
