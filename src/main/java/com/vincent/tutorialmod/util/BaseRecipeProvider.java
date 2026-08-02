/*
* This is done by vincent00tencniv
* This helper parent class helps with organizing and creating helper methods that would be useful for the actual data gen class
* For that class, refer to ModRecipeProvider.java
* */

package com.vincent.tutorialmod.util;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
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

public abstract class BaseRecipeProvider extends RecipeProvider {
    protected BaseRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    protected void pressurePlate(DeferredBlock<Block> pressurePlateBlock, DeferredBlock<Block> baseBlock) {
        pressurePlate(pressurePlateBlock.get(), baseBlock.get());
    }

    protected void pressurePlate(DeferredBlock<Block> pressurePlateBlock, DeferredBlock<Block> baseBlock, String groupName) {
        pressurePlate(pressurePlateBlock, baseBlock);
    }

    protected void pressurePlate(Block pressurePlateBlock, Block baseBlock, String groupName) {
        pressurePlate(pressurePlateBlock, baseBlock);
    }

    protected void slab(Block pressurePlateBlock, Block baseBlock, String groupName){
        slab(RecipeCategory.BUILDING_BLOCKS, pressurePlateBlock, baseBlock);
    }

    protected void slab(DeferredBlock<Block> pressurePlateBlock, DeferredBlock<Block> baseBlock, String groupName){
        slab(pressurePlateBlock.get(), baseBlock.get(), groupName);
    }

    protected void button(Block buttonBlock, Block baseBlock, String groupName){
        buttonBuilder(buttonBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void button(Block buttonBlock, Block baseBlock){
        buttonBuilder(buttonBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void button(DeferredBlock<Block> buttonBlock, DeferredBlock<Block> baseBlock, String groupName){
        button(buttonBlock.get(), baseBlock.get(), groupName);}

    protected void button(DeferredBlock<Block> buttonBlock, DeferredBlock<Block> baseBlock){
        button(buttonBlock.get(), baseBlock.get());
    }

    protected void stairs(Block stairBlock, Block baseBlock, String groupName){
        stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void stairs(Block stairBlock, Block baseBlock){
        stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void stairs(DeferredBlock<Block> stairBlock, DeferredBlock<Block> baseBlock, String groupName){
        stairs(stairBlock.get(), baseBlock.get(), groupName);
    }

    protected void stairs(DeferredBlock<Block> stairBlock, DeferredBlock<Block> baseBlock){
        stairs(stairBlock.get(), baseBlock.get());
    }

    protected void fence(Block fenceBlock, Block baseBlock, String groupName) {
        fenceBuilder(fenceBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void fence(Block fenceBlock, Block baseBlock) {
        fenceBuilder(fenceBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void fence(DeferredBlock<Block> fenceBlock, DeferredBlock<Block> baseBlock, String groupName) {
        fence(fenceBlock.get(), baseBlock.get(), groupName);
    }

    protected void fence(DeferredBlock<Block> fenceBlock, DeferredBlock<Block> baseBlock) {
        fence(fenceBlock.get(), baseBlock.get());
    }

    protected void fenceGate(Block fenceGateBlock, Block baseBlock, String groupName) {
        fenceGateBuilder(fenceGateBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void fenceGate(Block fenceGateBlock, Block baseBlock) {
        fenceGateBuilder(fenceGateBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void fenceGate(DeferredBlock<Block> fenceGateBlock, DeferredBlock<Block> baseBlock, String groupName) {
        fenceGate(fenceGateBlock.get(), baseBlock.get(), groupName);
    }

    protected void fenceGate(DeferredBlock<Block> fenceGateBlock, DeferredBlock<Block> baseBlock) {
        fenceGate(fenceGateBlock.get(), baseBlock.get());
    }

    protected void wall(DeferredBlock<Block> wallBlock, DeferredBlock<Block> baseBlock, String groupName) {
        wall(wallBlock.get(), baseBlock.get());
    }

    protected void wall(Block wallBlock, Block baseBlock, String groupName) {
        wall(wallBlock, baseBlock);
    }

    protected void wall(Block wallBlock, Block baseBlock) {
        wall(RecipeCategory.BUILDING_BLOCKS, wallBlock, baseBlock);
    }

    protected void wall(DeferredBlock<Block> wallBlock, DeferredBlock<Block> baseBlock) {
        wall(wallBlock.get(), baseBlock.get());
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

    // The following method is done by Kaupenjoe

    @Override
    public <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, List<ItemLike> smeltables,
                                                             RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result,
                                                             float experience, int cookingTime, String group, String fromDesc) {
        for(ItemLike itemlike : smeltables) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), craftingCategory, cookingCategory, result, experience, cookingTime, factory).group(group).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(output, TutorialMod.MOD_ID + ":" + getItemName(result) + fromDesc + "_" + getItemName(itemlike));
        }
    }
}
