/*
 * This is done by vincent00tencniv
 * This helper parent class fixes some problems in the ModelProvider.java class, and is used by the ModRecipeProvider
 * For that class, refer to ModModelProvider.java
 * */
package com.vincent.tutorialmod.util;

import com.vincent.tutorialmod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class FixedModelProvider extends ModelProvider {
    public FixedModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Deprecated
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        FixedBlockModelGenerators fixedBlockModelGenerators = new FixedBlockModelGenerators(blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput);
        FixedItemModelGenerators fixedItemModelGenerators = new FixedItemModelGenerators(itemModels.itemModelOutput, itemModels.modelOutput);
        registerModels(fixedBlockModelGenerators, fixedItemModelGenerators);
    }

    protected void registerModels(FixedBlockModelGenerators blockModels, ItemModelGenerators itemModels){
        super.registerModels(blockModels, new FixedItemModelGenerators(itemModels.itemModelOutput, itemModels.modelOutput));
    }

    protected void registerModels(BlockModelGenerators blockModels, FixedItemModelGenerators itemModels){
        super.registerModels(new FixedBlockModelGenerators(blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput), itemModels);
    }

    protected void registerModels(FixedBlockModelGenerators blockModels, FixedItemModelGenerators itemModels) {
        super.registerModels(blockModels, itemModels);
    }
}
