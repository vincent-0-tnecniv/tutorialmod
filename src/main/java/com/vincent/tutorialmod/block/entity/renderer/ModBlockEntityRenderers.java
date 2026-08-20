package com.vincent.tutorialmod.block.entity.renderer;

import com.vincent.tutorialmod.block.entity.ModBlockEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ModBlockEntityRenderers {
    public static void register(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
    }
}
