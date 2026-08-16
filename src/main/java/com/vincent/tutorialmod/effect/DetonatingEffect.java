package com.vincent.tutorialmod.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level.ExplosionInteraction;

public class DetonatingEffect extends MobEffect {
    protected DetonatingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onMobRemoved(ServerLevel level, LivingEntity mob, int amplifier, Entity.RemovalReason reason) {
        if(reason == Entity.RemovalReason.KILLED){
            level.explode(mob, mob.getX(), mob.getY(), mob.getZ(), 5.0f, ExplosionInteraction.NONE);
        }
        super.onMobRemoved(level, mob, amplifier, reason);
    }
}
