package com.vincent.tutorialmod.menu.custom;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.util.BaseAbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CrystallizerScreen extends BaseAbstractContainerScreen<CrystallizerMenu> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(
            TutorialMod.MOD_ID, "textures/gui/crystalizer/crystalizer_gui.png"
    );
    private static final Identifier ARROW_TEXTURE = Identifier.fromNamespaceAndPath(
            TutorialMod.MOD_ID, "textures/gui/crystalizer/arrow_progress.png"
    );
    private static final Identifier CRYSTAL_TEXTURE = Identifier.parse("textures/block/amethyst_cluster.png");

    public CrystallizerScreen(CrystallizerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        drawGuiAtCenter(graphics, GUI_TEXTURE);

        renderProgressArrow(graphics, x, y);
        renderProgressCrystal(graphics, x, y);
    }

    private void renderProgressArrow(GuiGraphicsExtractor guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE,x + 73, y + 35, 0, 0,
                    menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    private void renderProgressCrystal(GuiGraphicsExtractor guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CRYSTAL_TEXTURE,
                    x + 104, y + 13 + 16 - menu.getScaledCrystalProgress(),
                    0,16 - menu.getScaledCrystalProgress(),
                    16, menu.getScaledCrystalProgress(),
                    16, 16);
        }
    }
}
