package com.vincent.tutorialmod.compat;

// This is specific - for each new recipe to be displayed in JEI,
// one of such classes should be created

import com.mojang.datafixers.util.Pair;
import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.recipe.custom.CrystallizerRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

public class CrystallizerRecipeCategory implements IRecipeCategory<RecipeHolder<CrystallizerRecipe>> {

    // --- CONFIG AFTER COPY OVER ---
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID,
            "textures/gui/crystallizer/crystallizer_gui.png");
    // this needs to point to the texture of the GUI

    private static final ItemStack ICON_ITEMSTACK =
            new ItemStack(ModBlocks.CRYSTALLIZER.get());
    // this needs to have the block that the icon shows

    private static final Pair<Integer, Integer> INPUT_POS = Pair.of(54, 34);
    private static final Pair<Integer, Integer> OUTPUT_POS = Pair.of(104, 34);
    // this needs to update to the positions of the slots
    // first one is the X value
    // second one is the Y value

    private static final Pair<Integer, Integer> GUI_SIZE = Pair.of(176, 85);
    // this needs to update to the size of the GUI in the JEI menu

    // --- CONFIG IS DONE! ---


    private final IDrawable icon;
    private final IDrawable overlay;

    public CrystallizerRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(TEXTURE, 0, 0, GUI_SIZE.getFirst(), GUI_SIZE.getSecond());
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ICON_ITEMSTACK);
    }

    @Override
    public IRecipeType<RecipeHolder<CrystallizerRecipe>> getRecipeType() {
        return ModJEIPlugin.CRYSTALLIZER;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.tutorialmod.crystallizer");
    }

    @Override
    public int getWidth() {
        return GUI_SIZE.getFirst();
    }

    @Override
    public int getHeight() {
        return GUI_SIZE.getSecond();
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrystallizerRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POS.getFirst(), INPUT_POS.getSecond()).add(recipe.value().inputItem());

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_POS.getFirst(), OUTPUT_POS.getSecond()).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<CrystallizerRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);
    }
}
