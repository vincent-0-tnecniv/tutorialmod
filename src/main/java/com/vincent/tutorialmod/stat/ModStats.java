package com.vincent.tutorialmod.stat;

import com.vincent.tutorialmod.TutorialMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStats {
    public static final DeferredRegister<Identifier> CUSTOM_STATS =
            DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, TutorialMod.MOD_ID);

    public static final Supplier<Identifier> VALUABLES_FOUND = makeCustomStat("valuables_found");
    public static final Supplier<Identifier> TIMES_PARRIED = makeCustomStat("times_parried");

    private static Supplier<Identifier> makeCustomStat(String key){
        Identifier statIdentifer = Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, key);
        return CUSTOM_STATS.register(key, () -> statIdentifer);
    }

    public static void register(IEventBus bus) {
        CUSTOM_STATS.register(bus);
    }
}
