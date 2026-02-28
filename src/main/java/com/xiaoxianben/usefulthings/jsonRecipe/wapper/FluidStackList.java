package com.xiaoxianben.usefulthings.jsonRecipe.wapper;

import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FluidStackList implements Iterable<FluidStack> {
    public final List<FluidStack> list = new ArrayList<>();

    public FluidStackList(FluidStack... stacks) {
        list.addAll(Arrays.asList(stacks));
    }

    public FluidStackList() {
    }

    public boolean contains(FluidStack stack) {
        return getDafaultItemStack(stack) != null;
    }

    public FluidStack getDafaultItemStack(FluidStack stack) {
        for (FluidStack stack1 : list) {
            if (RecipeTypes.recipe_fluidStack.equals(stack1, stack)) {
                return stack1.copy();
            }
        }
        return null;
    }

    public FluidStackList copy() {
        FluidStackList itemStackList = new FluidStackList();
        itemStackList.list.addAll(list);
        itemStackList.list.replaceAll(FluidStack::copy);
        return itemStackList;
    }

    @Nonnull
    @Override
    public Iterator<FluidStack> iterator() {
        return list.iterator();
    }
}
