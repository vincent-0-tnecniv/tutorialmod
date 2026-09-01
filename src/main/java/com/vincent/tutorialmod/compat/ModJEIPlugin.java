package com.vincent.tutorialmod.compat;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.menu.custom.CrystallizerScreen;
import com.vincent.tutorialmod.recipe.ModRecipes;
import com.vincent.tutorialmod.recipe.custom.CrystallizerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;

// This is general - whenever JEI compat is supported, this MUST exist

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {
    private static RecipeMap syncedRecipes = RecipeMap.EMPTY;

    // --- CONFIG WHEN ADDING MORE RECIPES ---
    public static final IRecipeType<RecipeHolder<CrystallizerRecipe>> CRYSTALLIZER =
            createRecipeHolderType("crystallizing");
    // create a holder

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new CrystallizerRecipeCategory(registration.getJeiHelpers().getGuiHelper())
                // add a new line with the new category when there are more
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CRYSTALLIZER, this.getRecipes(syncedRecipes, ModRecipes.CRYSTALLIZER_ST_PAIR.TYPE.get()));
        // add the recipes of that recipe type
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrystallizerScreen.class, 74, 30, 22, 28);
        // added the ability to display all recipes by clicking the progress arrow
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(CRYSTALLIZER, new ItemStack(ModBlocks.CRYSTALLIZER));
        // allows the ability to display recipes with the crafting block
        // this is done in game using the U key (default)
    }

    @EventBusSubscriber(modid = TutorialMod.MOD_ID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(
                    ModRecipes.CRYSTALLIZER_ST_PAIR.TYPE.get()
                    // add the recipe type here for data pack syncing
            );
        }
    }

    @EventBusSubscriber(modid = TutorialMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipeReceived(RecipesReceivedEvent event) {
            syncedRecipes = event.getRecipeMap();
            // add the recipe type here for data pack retrival
        }
    }
    // --- CONFIG IS DONE! ---



    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "jei_plugin");
    }

    // From Occultism
    // Under MIT License
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        return (List) recipeMap.byType(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> IRecipeType<T> createRecipeHolderType(String path) {
        return (IRecipeType<T>) IRecipeType.create(Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, path), RecipeHolder.class);
    }
}
