package com.vincent.tutorialmod.villager;

import com.google.common.collect.ImmutableSet;
import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.datagen.villager.datapack.ModTradeSets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {

    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, TutorialMod.MOD_ID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, TutorialMod.MOD_ID);

    public static final Holder<PoiType> KAUPEN_POI = POI_TYPES.register("kaupen_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.MAGIC_BLOCK.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> KAUPENGER = simpleVillager("kaupenger", KAUPEN_POI, SoundEvents.AMETHYST_BLOCK_CHIME,
            Int2ObjectMap.ofEntries(
            Int2ObjectMap.entry(1, ModTradeSets.KAUPENGER_LEVEL_1),
            Int2ObjectMap.entry(2, ModTradeSets.KAUPENGER_LEVEL_2))
    );

    public static Holder<VillagerProfession> simpleVillager(String id, Holder<PoiType> poiType, SoundEvent workSound, Int2ObjectMap<ResourceKey<TradeSet>> map) {
        return VILLAGER_PROFESSIONS.register(id,
                () -> new VillagerProfession(Component.translatable("entity.minecraft.villager." + TutorialMod.MOD_ID + "." + id), holder -> holder.value() == poiType.value(),
                        holder -> holder.value() == poiType.value(), ImmutableSet.of(), ImmutableSet.of(),
                        workSound, map
                ));
    }

    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
