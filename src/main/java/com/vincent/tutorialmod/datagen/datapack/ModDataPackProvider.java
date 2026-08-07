package com.vincent.tutorialmod.datagen.datapack;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.datagen.datapack.damage_type.ModDamageTypes;
import com.vincent.tutorialmod.datagen.datapack.paintings.ModPaintings;
import com.vincent.tutorialmod.datagen.datapack.sounds.ModJukeboxSongs;
import com.vincent.tutorialmod.datagen.villager.datapack.ModTradeSets;
import com.vincent.tutorialmod.datagen.villager.datapack.ModVillagerTrades;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.PAINTING_VARIANT, ModPaintings::bootstrap)
            .add(Registries.JUKEBOX_SONG, ModJukeboxSongs::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
            .add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap)
            .add(Registries.TRADE_SET, ModTradeSets::bootstrap);

    public ModDataPackProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, BUILDER, Set.of(TutorialMod.MOD_ID));
    }
}
