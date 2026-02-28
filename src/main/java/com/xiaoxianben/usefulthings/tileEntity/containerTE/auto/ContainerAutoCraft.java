package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.inventory.InventoryCraftingFalse;
import com.xiaoxianben.usefulthings.jsonRecipe.recipeType.RecipeTypes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoCraft extends ContainerAutoBase {

    private final ItemStackHandlerAutoCraft handler = new ItemStackHandlerAutoCraft();
    private final InventoryCraftingFalse craftMatrix = new InventoryCraftingFalse(3, 3);
    public IRecipe iRecipe = null;


    public ContainerAutoCraft() {
    }

    @Override
    public boolean hasItemStackHandler() {
        return true;
    }

    @Override
    public boolean hasFluidTank() {
        return false;
    }

    @Override
    public boolean hasEnergyStorage() {
        return false;
    }

    @Nullable
    @Override
    public ItemStackHandlerBase[] getItemStackHandler() {
        return new ItemStackHandlerBase[]{handler};
    }

    @Nullable
    @Override
    public FluidTank[] getFluidTank() {
        return null;
    }

    @Nullable
    @Override
    public EnergyStorageBase getEnergyStorage() {
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        backCraftMatrix();

        NBTTagCompound nbt = super.serializeNBT();

        updateCraftMatrix();

        if (iRecipe != null) {
            nbt.setString("iRecipe", iRecipe.getRegistryName().toString());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);

        setRecipe(CraftingManager.getRecipe(new ResourceLocation(nbt.getString("iRecipe"))));
        updateCraftMatrix();
    }

    public void setRecipe(IRecipe iRecipe) {
        this.iRecipe = iRecipe;
    }

    public boolean canStart() {
        if (iRecipe == null) {
            return false;
        }
        if (!craftMatrix.isEmpty()) {
            // 检测能否输出
            ItemStack craftingResult = iRecipe.getCraftingResult(craftMatrix);
            ItemStack outputOld = handler.getStackInSlot(20);
            if (!outputOld.isEmpty()) {
                ItemStack inserted = handler.insertItemInternal(20, craftingResult, true);
                return inserted.isEmpty();
            }
            return true;
        } else return updateCraftMatrix();
    }

    public void processFinish() {
        ItemStack craftingResult = iRecipe.getCraftingResult(craftMatrix);
        NonNullList<ItemStack> remainingItems = iRecipe.getRemainingItems(craftMatrix);

        // 尝试输出物品
        ItemStack inserted1 = handler.insertItemInternal(20, craftingResult, true);
        if (inserted1.isEmpty()) {
            handler.insertItemInternal(20, craftingResult, false);
        } else {
            backCraftMatrix();
            return;
        }

        // 输出剩余物品
        for (ItemStack remainingItem : remainingItems) {
            if (!remainingItem.isEmpty()) {
                ItemStack inserted = remainingItem.copy();
                for (int i = 0; i < ItemStackHandlerAutoCraft.inputSlots; i++) {
                    inserted = handler.insertItemInternal(i, inserted, false);
                    if (inserted.isEmpty()) {
                        break;
                    }
                }
            }
        }

        craftMatrix.clear();
    }

    @Override
    public void processOff() {
        backCraftMatrix();
    }

    /**
     * 根据{@code iRecipe}更新{@code craftMatrix}，不会回退已经放入{@code craftMatrix}的物品，
     * 仅会放入新的物品，如果要放入新的物品，请调用{@code backCraftMatrix}
     *
     * @return 是否成功放入，如果失败，不需要自己调用{@code backCraftMatrix}
     */
    protected boolean updateCraftMatrix() {
        craftMatrix.clear();
        if (iRecipe == null) {
            return false;
        }
        NonNullList<Ingredient> recipeIngredients = iRecipe.getIngredients();

        for (Ingredient ingredient : recipeIngredients) {
            int indexItem = -1;
            check:
            for (ItemStack matchingStack : ingredient.getMatchingStacks()) {
                for (int i = 0; i < handler.getSlots() - 1; i++) {
                    if (RecipeTypes.recipe_itemStack.equals(matchingStack, handler.getStackInSlot(i))) {
                        indexItem = i;
                        break check;
                    }
                }
            }
            if (indexItem == -1) {
                backCraftMatrix();
                return false;
            }
            ItemStack copy = handler.getStackInSlot(indexItem).copy();
            copy.setCount(1);
            craftMatrix.setInventorySlotContents(indexItem, copy);
            ItemStack extracted = handler.extractItemInternal(indexItem, 1, false);
        }
        return true;
    }

    /**
     * 回退{@code craftMatrix}，会回退已经放入{@code craftMatrix}的物品
     */
    protected void backCraftMatrix() {
        if (craftMatrix.isEmpty()) {
            return;
        }
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
            ItemStack inserted = craftMatrix.getStackInSlot(i).copy();

            for (int i1 = 0; i1 < handler.getSlots(); i1++) {
                if (inserted.isEmpty()) {
                    break;
                }
                inserted = handler.insertItemInternal(i1, inserted, false);
            }
        }
        craftMatrix.clear();
    }


    static class ItemStackHandlerAutoCraft extends ItemStackHandlerBase {
        /**
         * 输入物品槽位数量, 也是输出格子的索引位置
         */
        public static final int inputSlots = 20;

        public ItemStackHandlerAutoCraft() {
            super(21);
        }

        @Override
        public boolean canExtractItem(int slot) {
            return slot == 20;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return slot < 20 && slot > 0;
        }
    }
}