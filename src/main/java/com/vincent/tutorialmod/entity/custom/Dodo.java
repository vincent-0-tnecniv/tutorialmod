package com.vincent.tutorialmod.entity.custom;

import com.vincent.tutorialmod.entity.variant.DodoVariant;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class Dodo extends PathfinderMob {

    public static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(Dodo.class, EntityDataSerializers.INT);

    // Note: for the idle animation to play, it is very important to know the length of the idle animation
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final int TPS = 20;

    public Dodo(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        // "Lower priority means higher priority"
        // In human language, 0 has the highest priority to be performed
        // Then 1,2,3... so on
        goalSelector.addGoal(0, new FloatGoal(this));
        // This usually has to be the highest priority
        // Otherwise the mob would just sink like a zombie would

        goalSelector.addGoal(1, new PanicGoal(this, 2D));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 7F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
            // Remember that the server does not need to know how entities look!
            // This matters only on the client side!
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 2 * TPS;
            // This is where the length of the animation matters!
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        // Important: Setting up attributes are VERY important
        // Just like that from the bloodnova, the entity cannot spawn without attributes!
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.TEMPT_RANGE, 16D)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(VARIANT, 0); // Any EntityDataAccessor needs this!
    }

    private int getTypeVariant() {
        return this.getEntityData().get(VARIANT);
    }

    public DodoVariant getVariant() {
        return DodoVariant.byId(getTypeVariant());
    }

    private void setVariant(DodoVariant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(VARIANT, input.getIntOr("Variant",0));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        DodoVariant variant = Util.getRandom(DodoVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }
}
