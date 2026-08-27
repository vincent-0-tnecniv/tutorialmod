package com.vincent.tutorialmod.menu.custom;

import com.vincent.tutorialmod.TutorialMod;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class PedestalScreen extends AbstractContainerScreen<PedestalMenu> {

    // Renderer, but for the screen (Client Side)

    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "textures/gui/pedestal/pedestal_gui.png");

    public PedestalScreen(PedestalMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int centerX = (width - imageWidth) / 2;
        int centerY = (height - imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, centerX, centerY, 0 , 0,
                imageWidth, imageHeight, 256, 256);
    }
}
