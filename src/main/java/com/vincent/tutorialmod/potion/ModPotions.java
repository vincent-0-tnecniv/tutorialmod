package com.vincent.tutorialmod.potion;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, TutorialMod.MOD_ID);

    public static final Holder<Potion> STINKY_POTION = POTIONS.register("stinky_potion",
            () -> new Potion("stinky_potion", new MobEffectInstance(ModEffects.STINKY_EFFECT, 1200, 0)));

    public static final Holder<Potion> STINKY_LONG_POTION = POTIONS.register("stinky_long_potion",
            () -> new Potion("stinky_long_potion", new MobEffectInstance(ModEffects.STINKY_EFFECT, 3600, 0)));

    public static final Holder<Potion> STINKY_STRONG_POTION = POTIONS.register("stinky_strong_potion",
            () -> new Potion("stinky_strong_potion", new MobEffectInstance(ModEffects.STINKY_EFFECT, 600, 1)));

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.DIRT, ModPotions.STINKY_POTION);
        builder.addMix(ModPotions.STINKY_POTION, Items.REDSTONE, ModPotions.STINKY_LONG_POTION);
        builder.addMix(ModPotions.STINKY_POTION, Items.GLOWSTONE_DUST, ModPotions.STINKY_STRONG_POTION);
    }

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
