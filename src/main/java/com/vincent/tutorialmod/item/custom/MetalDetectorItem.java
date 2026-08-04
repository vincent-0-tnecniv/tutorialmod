package com.vincent.tutorialmod.item.custom;

import com.vincent.tutorialmod.data.ModDataComponents;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.tags.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos positionClicked = context.getClickedPos();
        Player player = context.getPlayer();
        if(player == null) return InteractionResult.SUCCESS;

        if(!level.isClientSide()){
            boolean foundBlock = false;
            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState blockstate = level.getBlockState(positionClicked.below(i));
                if(isValuableBlock(blockstate)){
                    outputValuableCoordinates(positionClicked.below(i), player, blockstate.getBlock());
                    foundBlock = true;

                    context.getItemInHand().hurtAndBreak(1, player, context.getHand());

                    level.playSound(null, positionClicked, SoundEvents.AMETHYST_BLOCK_CHIME,
                            SoundSource.BLOCKS, 1.5f, 1f);

                    spawnFoundParticles(level, positionClicked, blockstate);

                    addDataToDataTablet(player, positionClicked.below(i));

                    break;
                }
            }

            if(!foundBlock){
                outputNoValuablesFound(player);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private void addDataToDataTablet(Player player, BlockPos position) {
        int slotIndex = player.getInventory().findSlotMatchingItem(new ItemStack(ModItems.DATA_TABLET.get()));
        // As the ItemStack to be found is a new item stack,
        // the finding with ignore any data tablets with coordinates
        if(slotIndex == -1) {
            return;
        }
        ItemStack dataTablet = player.getInventory().getItem(slotIndex);
        dataTablet.set(ModDataComponents.COORDINATES, position);
    }

    private void spawnFoundParticles(Level level, BlockPos positionClicked, BlockState blockstate) {
        for(int i = 0; i < 20; i++){
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockstate),
                    positionClicked.getX() + 0.5d, positionClicked.getY() + 0.5d, positionClicked.getZ() + 0.5d,
                    1, Math.cos(i * 18) * 0.15d, 0.15d, Math.sin(i * 18) * 0.15d, 0.1);
        }
    }

    private void outputNoValuablesFound(Player player) {
        player.sendSystemMessage(Component.translatable("item.tutorialmod.metal_detector.no_valuables"));
    }

    private void outputValuableCoordinates(BlockPos position, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Valuables found at: ")
                .append(block.getName().append(" at (" + position.getX() +
                        " ," + position.getY() + " ," + position.getZ() + ")")));
    }

    private boolean isValuableBlock(BlockState blockstate) {
        return blockstate.is(ModTags.Blocks.METAL_DETECTABLES);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        // This is always called on ONLY the client!
        if(Minecraft.getInstance().hasShiftDown()){
            builder.accept(Component.translatable("tooltip.tutorialmod.metal_detector.shift_down"));
        } else{
            builder.accept(Component.translatable("tooltip.tutorialmod.metal_detector"));
        }

        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
