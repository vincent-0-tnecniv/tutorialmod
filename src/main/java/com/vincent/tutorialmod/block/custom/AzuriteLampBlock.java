package com.vincent.tutorialmod.block.custom;

import com.vincent.tutorialmod.util.BlockWithState;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class AzuriteLampBlock extends BlockWithState {

    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");
    public static final IntegerProperty TOC =  IntegerProperty.create("toc", 0, 3);

    public AzuriteLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CLICKED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            level.playSound(null, pos, SoundEvents.LEVER_CLICK,  SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, state.cycle(CLICKED));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICKED);
    }
}
