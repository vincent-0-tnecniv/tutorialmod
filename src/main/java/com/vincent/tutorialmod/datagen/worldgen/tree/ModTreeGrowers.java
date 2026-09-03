package com.vincent.tutorialmod.datagen.worldgen.tree;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.datagen.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower DRIFTWOOD = new TreeGrower(TutorialMod.MOD_ID + ":driftwood",
            Optional.empty(), // mega trees
            Optional.of(ModConfiguredFeatures.DRIFTWOOD_KEY), // normal trees
            Optional.empty() // flowers
    );
}
