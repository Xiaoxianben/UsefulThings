package com.xiaoxianben.usefulthings.jsonRecipe.wapper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackAndInt {
    public ItemStack itemStack;
    public int intValue;

    public ItemStackAndInt(ItemStack itemStack, int intValue) {
        this.itemStack = itemStack;
        this.intValue = intValue;
    }

    public ItemStackAndInt(Item item, int intValue) {
        this.itemStack = new ItemStack(item);
        this.intValue = intValue;
    }
}
