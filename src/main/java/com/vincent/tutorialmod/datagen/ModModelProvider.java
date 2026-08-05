package com.vincent.tutorialmod.datagen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.block.custom.AzuriteLampBlock;
import com.vincent.tutorialmod.data.ModDataComponents;
import com.vincent.tutorialmod.item.ModArmorMaterials;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.util.datagen.FixedBlockModelGenerators;
import com.vincent.tutorialmod.util.datagen.FixedItemModelGenerators;
import com.vincent.tutorialmod.util.datagen.FixedModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Optional;

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

        registerDataComponentModels(itemModels, ModItems.DATA_TABLET.get(), "_on", ModDataComponents.COORDINATES.get());

        itemModels.generateBow(ModItems.KAUPEN_BOW.get());
        // Because of the custom changes in the FixedItemModelGernator class,
        // only calling the generateBow() method will work once and for all

        itemModels.declareCustomModelItem(ModItems.BLIZZARD_STAFF.get());

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

        MultiVariant litVariant = BlockModelGenerators.plainVariant(
                blockModels.createSuffixedVariant(
                        ModBlocks.AZURITE_LAMP.get(), "_on",
                        ModelTemplates.CUBE_ALL, TextureMapping::cube
                )
        );

        MultiVariant defaultVariant = BlockModelGenerators.plainVariant(
                TexturedModel.CUBE.create(
                        ModBlocks.AZURITE_LAMP.get(), blockModels.modelOutput
                )
        );

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.AZURITE_LAMP.get()).with(
                        BlockModelGenerators.createBooleanModelDispatch(AzuriteLampBlock.CLICKED,
                                litVariant, // This stores the lit variant
                                defaultVariant // This stores the default variant (unlit)
                                // Technically, with (N-1) of such modules, this can be done
                                // so that N variants are supported

                                // Tip of Vincent: Recall about "The Piercer" in Fabric!
                                // N is 3 there!
                        )
                )
        );

        blockModels.createNonTemplateModelBlock(ModBlocks.PEDESTAL.get());
    }


    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, Item item, String switchSuffix, DataComponentType<?> component, ModelTemplate template) {
        ItemModel.Unbaked unbakedDataTablet = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, template));
        ItemModel.Unbaked unbakedDataTabletOn = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, switchSuffix, template));
        itemModels.itemModelOutput.register(item,
                new ClientItem(new ConditionalItemModel.Unbaked(Optional.empty(), new HasComponent(component, false),
                        unbakedDataTabletOn, unbakedDataTablet), new ClientItem.Properties(false, false, 1f)));
    }

//    Human-readable format of the above method:
//    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, Item item, String switchSuffix, DataComponentType<?> component, ModelTemplate template) {
//        ItemModel.Unbaked unbakedItemModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, template));
//        ItemModel.Unbaked unbakedItemOtherModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, switchSuffix, template));
//        HasComponent hasComponent = new HasComponent(component, false);
//        ConditionalItemModel.Unbaked unbakedConditionalItemModel = new ConditionalItemModel.Unbaked(Optional.empty(), hasComponent, unbakedItemOtherModel, unbakedItemModel);
//        ClientItem.Properties clientItemProperties = new ClientItem.Properties(false, false, 1f);
//
//        ClientItem clientItem = new ClientItem(unbakedConditionalItemModel, clientItemProperties);
//
//        itemModels.itemModelOutput.register(item, clientItem);
//    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, Item item, String switchSuffix, DataComponentType<?> component) {
        registerDataComponentModels(itemModels, item, switchSuffix, component, ModelTemplates.FLAT_ITEM);
    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, DeferredItem<Item> item, String switchSuffix, DataComponentType<?> component) {
        registerDataComponentModels(itemModels, item.get(), switchSuffix, component);
    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, DeferredItem<Item> item, String switchSuffix, DataComponentType<?> component, ModelTemplate template){
        registerDataComponentModels(itemModels, item.get(), switchSuffix, component, template);
    }
}
