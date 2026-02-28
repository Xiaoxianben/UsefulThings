package com.xiaoxianben.usefulthings.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;

/**
 * This class is used to get recipes (IRecipe requires it...) with a Container. <br>
 * From CoFH/CoFHCore-1.12-Legacy {cofh.core.inventory.InventoryCraftingFalse}
 *
 * @author King Lemming(CoFH)
 */
public final class InventoryCraftingFalse extends InventoryCrafting {

    private static final NullContainer nullContainer = new NullContainer();

    /* NULL INNER CLASS */
    public static class NullContainer extends Container {

        @Override
        public void onCraftMatrixChanged(IInventory inventory) {

        }

        @Override
        public boolean canInteractWith(EntityPlayer player) {

            return false;
        }

    }

    public InventoryCraftingFalse(int width, int height) {
        super(nullContainer, width, height);
    }

}