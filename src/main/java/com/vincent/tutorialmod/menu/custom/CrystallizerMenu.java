package com.vincent.tutorialmod.menu.custom;

import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.block.entity.custom.CrystallizerBlockEntity;
import com.vincent.tutorialmod.menu.ModMenuTypes;
import com.vincent.tutorialmod.util.BaseAbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class CrystallizerMenu extends BaseAbstractContainerMenu {

    public final CrystallizerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public CrystallizerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
                new ItemStacksResourceHandler(2), new SimpleContainerData(2));
    }

    public CrystallizerMenu(int pContainerId, Inventory inv, BlockEntity entity,
                            ItemStacksResourceHandler handler, ContainerData data) {
        super(ModMenuTypes.CRYSTALLIZER_MENU.get(), pContainerId);

        blockEntity = (CrystallizerBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new ResourceHandlerSlot(handler, handler::set, 0, 54, 34));
        this.addSlot(new ResourceHandlerSlot(handler, handler::set, 1, 104, 34) {
            @Override
            public boolean mayPlace(ItemStack stack) {return false;}
            // This makes items not insertable - just like the output slot of a furnace
        });

        addDataSlots(data);
        // If there is ContainerData, this method MUST be used!
    }

    @Override
    protected int getCustomInventorySlots() {
        return 2;
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
        return ModBlocks.CRYSTALLIZER.get();
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    public int getScaledCrystalProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int crystalPixelSize = 16;

        return maxProgress != 0 && progress != 0 ? progress * crystalPixelSize / maxProgress : 0;
    }
}
