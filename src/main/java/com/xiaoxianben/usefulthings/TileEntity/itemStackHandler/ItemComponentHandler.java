package com.xiaoxianben.usefulthings.TileEntity.itemStackHandler;

import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.item.ItemComponent;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemComponentHandler extends net.minecraftforge.items.ItemStackHandler {


    public static final List<ItemComponent> machine = Arrays.asList(ModItems.component_machine);
    public static final List<ItemComponent> empty = new ArrayList<>();


    public final List<ItemComponent> canInsertComponents;


    public ItemComponentHandler(int size, List<ItemComponent> components) {
        super(size);
        canInsertComponents = components;
    }


    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
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
        return !stack.isEmpty() && slot < this.stacks.size() && stack.getItem() instanceof ItemComponent && this.canInsertComponents.contains((ItemComponent) stack.getItem());
    }

    public int getComponent(ItemComponent item) {
        return this.stacks.stream().filter(stack -> stack.getItem() == item).findFirst().orElse(ItemStack.EMPTY).getCount();
    }

}
