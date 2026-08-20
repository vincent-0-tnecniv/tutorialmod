package com.vincent.tutorialmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.vincent.tutorialmod.block.entity.custom.PedestalBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity, PedestalBlockEntityRenderState> {

    private final ItemModelResolver itemModelResolver;

    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public PedestalBlockEntityRenderState createRenderState() {
        return new PedestalBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(PedestalBlockEntity blockEntity, PedestalBlockEntityRenderState state, float partialTicks,
                                   Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        state.level = blockEntity.getLevel();
        if(blockEntity.getLevel() == null) return;
        state.rotation = (blockEntity.getLevel().getGameTime() + partialTicks * 0.5f) % 360f;

        itemModelResolver.updateForTopItem(state.itemStackRenderState,
                blockEntity.inventory.getResource(0).toStack(), ItemDisplayContext.FIXED,
                blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(PedestalBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();

        poseStack.translate(0.5D, 1.15D, 0.5D);
        // Translate up / down / left / right
        poseStack.scale(0.5F, 0.5F, 0.5F);
        // Scale up / down (look bigger / smaller)
        poseStack.mulPose(Axis.YP.rotationDegrees(state.rotation));
        // Rotate some degress on the axis YP (Y positive)

        state.itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}
