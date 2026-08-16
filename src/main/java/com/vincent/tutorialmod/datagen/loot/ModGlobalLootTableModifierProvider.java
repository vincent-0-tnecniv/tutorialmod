package com.vincent.tutorialmod.datagen.loot;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.util.datagen.BaseGlobalLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootTableModifierProvider extends BaseGlobalLootModifierProvider {
    public ModGlobalLootTableModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TutorialMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("onion_seeds_to_grass",
                simpleBlockDrop(List.of(
                        Blocks.TALL_GRASS,
                        Blocks.SHORT_GRASS
                ),
                        ModExtraLootProvider.ONION_SEEDS
                ));

        add("metal_detector_from_jungle_temple",
                simpleChestLoot(List.of(
                        BuiltinStructures.JUNGLE_TEMPLE
                ),
                        ModExtraLootProvider.METAL_DETECTOR_FOUND
                ));

        add("raw_azurite_from_creeper",
                simpleEntityLoot(List.of(
                        EntityTypes.CREEPER
                ),
                        ModExtraLootProvider.RAW_AZURITE
                ));
    }
}
