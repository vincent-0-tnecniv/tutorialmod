package com.vincent.tutorialmod.entity;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.entity.custom.Dodo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.createEntities(TutorialMod.MOD_ID);

    public static final ResourceKey<EntityType<?>> DODO_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "dodo"));

    public static final Supplier<EntityType<Dodo>> DODO = create("dodo", Dodo::new, 1f, 2.5f, DODO_KEY);

    private static <T extends Entity> Supplier<EntityType<T>> create(String name, EntityType.EntityFactory<T> constructor, float width, float height, ResourceKey<EntityType<?>> key) {
        return ENTITY_TYPES.register(name,
                () -> EntityType.Builder.of(constructor, MobCategory.CREATURE).sized(width, height).build(key));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
