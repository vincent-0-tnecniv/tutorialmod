package com.vincent.tutorialmod.entity.client;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.entity.custom.Dodo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class DodoRenderer extends MobRenderer<Dodo, DodoRenderState, DodoModel> {

    public DodoRenderer(EntityRendererProvider.Context context) {
        super(context, new DodoModel(context.bakeLayer(ModModelLayerLocations.DODO)), 0.65f);
    }

    @Override
    public Identifier getTextureLocation(DodoRenderState dodoRenderState) {
        return Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "textures/entity/dodo/dodo.png");
    }

    @Override
    public DodoRenderState createRenderState() {
        return new DodoRenderState();
    }

    @Override
    public void extractRenderState(Dodo entity, DodoRenderState state, float partialTicks) {
        // If there is information in the entity object, but the rendering needs it, this is NEEDED!
        // e.g. the Ender Guardian showing its shulker inside - that is done using this!
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }
}
