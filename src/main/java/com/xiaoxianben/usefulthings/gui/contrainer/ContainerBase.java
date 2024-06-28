package com.xiaoxianben.usefulthings.gui.contrainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContainerBase extends Container {


    public List<Rectangle> guiExtraAreas = new ArrayList<>();


    public ContainerBase(EntityPlayer player) {

        //将玩家物品槽第一行（快捷栏）加入容器
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        // 将背包槽添加进容器
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

    }


    /**
     * 获取 额外的GUI矩形。
     */
    public List<Rectangle> getGuiExtraAreas() {
        return guiExtraAreas;
    }


    @ParametersAreNonnullByDefault
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index < 36) {
                if (!this.mergeItemStack(stack, 36, this.inventorySlots.size(), true)) {
                    int start = (index < 9) ? 9 : 0;
                    int end = (index < 9) ? this.inventorySlots.size() : 9;
                    if (!this.mergeItemStack(stack, start, end, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.mergeItemStack(stack, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }
            slot.onSlotChange(stack, itemstack);
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, stack);
        }
        return itemstack;
    }
}
