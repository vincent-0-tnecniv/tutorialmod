package com.vincent.tutorialmod.menu.custom;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.util.BaseAbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class PedestalScreen extends BaseAbstractContainerScreen<PedestalMenu> {

    // Renderer, but for the screen (Client Side)

    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "textures/gui/pedestal/pedestal_gui.png");

    public PedestalScreen(PedestalMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        drawGuiAtCenter(graphics, GUI_TEXTURE, x, y);
    }
}
