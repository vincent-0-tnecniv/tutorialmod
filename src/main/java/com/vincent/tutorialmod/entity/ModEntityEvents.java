package com.vincent.tutorialmod.entity;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.entity.client.DodoModel;
import com.vincent.tutorialmod.entity.client.DodoRenderer;
import com.vincent.tutorialmod.entity.client.ModModelLayerLocations;
import com.vincent.tutorialmod.entity.custom.Dodo;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModEntityEvents {

    @SubscribeEvent
    private static void setupRenderers(FMLCommonSetupEvent event) {
        // register renderers here
        EntityRenderers.register(ModEntities.DODO.get(), DodoRenderer::new);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // register attributes here
        event.put(ModEntities.DODO.get(), Dodo.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        // register layers here
        event.registerLayerDefinition(ModModelLayerLocations.DODO, DodoModel::createBodyLayer);
    }
}
