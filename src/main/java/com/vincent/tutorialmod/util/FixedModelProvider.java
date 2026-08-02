/*
 * This is done by vincent00tencniv
 * This helper parent class fixes some problems in the ModelProvider.java class, and is used by the ModRecipeProvider
 * For that class, refer to ModModelProvider.java
 * */
package com.vincent.tutorialmod.util;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class FixedModelProvider extends ModelProvider {
    public FixedModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Deprecated
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        FixedBlockModelGenerators fixedBlockModelGenerators = new FixedBlockModelGenerators(blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput);
        registerModels(fixedBlockModelGenerators, itemModels);
    }

    protected void registerModels(FixedBlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        super.registerModels(blockModels, itemModels);
    }
}
