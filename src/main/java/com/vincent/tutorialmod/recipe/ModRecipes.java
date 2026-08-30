package com.vincent.tutorialmod.recipe;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.recipe.custom.CrystallizerRecipe;
import com.vincent.tutorialmod.recipe.custom.CrystallizerRecipeInput;
import com.vincent.tutorialmod.util.RecipeEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, TutorialMod.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, TutorialMod.MOD_ID);

    public static final RecipeEntry<CrystallizerRecipeInput, CrystallizerRecipe> CRYSTALLIZER_ST_PAIR =
            RecipeEntry.create("crystallizing", CrystallizerRecipe.CODEC, CrystallizerRecipe.STREAM_CODEC);

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
        TYPES.register(bus);
    }
}
