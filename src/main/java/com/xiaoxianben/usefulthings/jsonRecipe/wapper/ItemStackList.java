package com.xiaoxianben.usefulthings.jsonRecipe.wapper;

import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ItemStackList implements Iterable<ItemStack> {
    public final List<ItemStack> list = new ArrayList<>();

    public ItemStackList(Item... items) {
        Arrays.stream(items).map(ItemStack::new).forEach(list::add);
    }

    public ItemStackList(ItemStack... stacks) {
        list.addAll(Arrays.asList(stacks));
    }

    public ItemStackList() {
    }

    public boolean contains(ItemStack stack) {
        return !getDafaultItemStack(stack).isEmpty();
    }

    public ItemStack getDafaultItemStack(ItemStack itemStack) {
        for (ItemStack stack : list) {
            if (RecipeTypes.recipe_itemStack.equals(stack, itemStack)) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStackList copy() {
        ItemStackList itemStackList = new ItemStackList();
        itemStackList.list.addAll(list);
        itemStackList.list.replaceAll(ItemStack::copy);
        return itemStackList;
    }

    @Nonnull
    @Override
    public Iterator<ItemStack> iterator() {
        return list.iterator();
    }
}
