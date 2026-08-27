package com.vincent.tutorialmod.menu.custom;

import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.block.entity.custom.PedestalBlockEntity;
import com.vincent.tutorialmod.menu.ModMenuTypes;
import com.vincent.tutorialmod.util.BaseAbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class PedestalMenu extends BaseAbstractContainerMenu {

    // Handles user actions (Server Side)

    public final PedestalBlockEntity blockEntity;
    private final Level level;

    public PedestalMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new ItemStacksResourceHandler(1));
    }

    public PedestalMenu(int containerId, Inventory inv, BlockEntity blockEntity, ItemStacksResourceHandler handler) {
        super(ModMenuTypes.PEDESTAL_MENU.get(), containerId);
        this.blockEntity = (PedestalBlockEntity) blockEntity;
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addSlot(new ResourceHandlerSlot(handler, handler::set, 0, 80, 35) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        // xPosition and yPosition are those for the TOP LEFT CORNER of the slot!
    }

    @Override
    protected Level level() {
        return this.level;
    }

    @Override
    protected BlockEntity blockEntity() {
        return this.blockEntity;
    }

    @Override
    protected Block baseBlock() {
        return ModBlocks.PEDESTAL_BLOCK.get();
    }

    @Override
    protected int getCustomInventorySlots() {
        return 1;
    }
}
