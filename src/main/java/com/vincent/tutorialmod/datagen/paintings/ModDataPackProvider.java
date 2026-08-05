package com.vincent.tutorialmod.datagen.paintings;

import com.vincent.tutorialmod.TutorialMod;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.PAINTING_VARIANT, ModPaintings::boostrap);

    public ModDataPackProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, BUILDER, Set.of(TutorialMod.MOD_ID));
    }
}
