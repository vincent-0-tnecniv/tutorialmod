package com.vincent.tutorialmod.entity.client;

import com.vincent.tutorialmod.entity.variant.DodoVariant;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class DodoRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public DodoVariant variant;
}
