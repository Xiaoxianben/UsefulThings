package com.xiaoxianben.usefulthings.TileEntity.itemStackHandler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ItemStackHandlerInput extends net.minecraftforge.items.ItemStackHandler {


    public final List<Item> canInputItems;


    public ItemStackHandlerInput(int size, Item... canInputItems) {
        super(size);
        this.canInputItems = Arrays.asList(canInputItems);
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    public ItemStack extractItemPrivate(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (isItemValid(slot, stack)) {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return slot < this.stacks.size() && !stack.isEmpty() && this.canInputItems.contains(stack.getItem());
    }

}
