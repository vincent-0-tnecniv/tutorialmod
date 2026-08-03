package com.vincent.tutorialmod.datagen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.item.ModArmorMaterials;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.util.FixedBlockModelGenerators;
import com.vincent.tutorialmod.util.FixedItemModelGenerators;
import com.vincent.tutorialmod.util.FixedModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends FixedModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, TutorialMod.MOD_ID);
    }

    @Override
    protected void registerModels(FixedBlockModelGenerators blockModels, FixedItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.AZURITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_AZURITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.METAL_DETECTOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ONION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.END_FIRE_STARTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AZURITE_HORSE_ARMOR.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateAllTools(ModItems.AZURITE_SWORD, ModItems.AZURITE_PICKAXE, ModItems.AZURITE_AXE,
                ModItems.AZURITE_SHOVEL, ModItems.AZURITE_HOE, ModItems.AZURITE_SPEAR);

        itemModels.generateTrimmableArmor(ModArmorMaterials.AZURITE_KEY,
                ModItems.AZURITE_HELMET, ModItems.AZURITE_CHESTPLATE,
                ModItems.AZURITE_LEGGINGS, ModItems.AZURITE_BOOTS,
                false);

//        blockModels.createTrivialCube(ModBlocks.AZURITE_BLOCK.get());
        // added with the block family
        blockModels.createTrivialCube(ModBlocks.RAW_AZURITE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.AZURITE_DEEPSLATE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.AZURITE_END_ORE.get());
        blockModels.createTrivialCube(ModBlocks.AZURITE_NETHER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.AZURITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.MAGIC_BLOCK.get());

        blockModels.createFamily(ModBlocks.AZURITE_BLOCK)
                .stairs(ModBlocks.AZURITE_STAIRS.get())
                .slab(ModBlocks.AZURITE_SLAB.get())
                .pressurePlate(ModBlocks.AZURITE_PRESSURE_PLATE.get())
                .button(ModBlocks.AZURITE_BUTTON.get())
                .fence(ModBlocks.AZURITE_FENCE.get())
                .fenceGate(ModBlocks.AZURITE_FENCE_GATE.get())
                .wall(ModBlocks.AZURITE_WALL.get())
                .door(ModBlocks.AZURITE_DOOR.get())
                .trapdoor(ModBlocks.AZURITE_TRAPDOOR.get());
    }
}
