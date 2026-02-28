package com.xiaoxianben.usefulthings.tileEntity.containerTE.auto;

import com.xiaoxianben.usefulthings.config.ConfigValue;
import com.xiaoxianben.usefulthings.init.ModJsonRecipes;
import com.xiaoxianben.usefulthings.tileEntity.energyStorage.EnergyStorageBase;
import com.xiaoxianben.usefulthings.tileEntity.itemStackHandler.ItemStackHandlerBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerAutoCharger extends ContainerAutoBase {
    private final ItemStackHandlerAutoCharger handler;

    public ContainerAutoCharger() {
        handler = new ItemStackHandlerAutoCharger();
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

    public boolean canStart() {
        final ItemStack stackInSlot = handler.getStackInSlot(0);
        if (stackInSlot.isEmpty()) {
            return false;
        }

        IEnergyStorage iEnergyStorage = stackInSlot.getCapability(CapabilityEnergy.ENERGY, null);
        if(iEnergyStorage != null && iEnergyStorage.canReceive() && iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored()) {
            return true;
        }

        int index = ModJsonRecipes.recipe_charger.getIndex(stackInSlot);
        if(index != -1) {
            ItemStack output = ModJsonRecipes.recipe_charger.getOutput(index);
            ItemStack input = ModJsonRecipes.recipe_charger.getInput(index);

            return handler.extractItemInternal(0, input.getCount(), true).getCount() == input.getCount() &&
                    handler.insertItemInternal(1, output.copy(), true).isEmpty();
        }

        return false;
    }


    public void processFinish() {
        final ItemStack stackInSlot = handler.getStackInSlot(0);

        // 正常充电
        IEnergyStorage iEnergyStorage = stackInSlot.getCapability(CapabilityEnergy.ENERGY, null);
        if (iEnergyStorage != null && iEnergyStorage.canReceive() && iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored()) {
            iEnergyStorage.receiveEnergy(ConfigValue.auto_charge_energy_finish, false);
            if (iEnergyStorage.getEnergyStored() >= iEnergyStorage.getMaxEnergyStored()) {
                ItemStack extracted = handler.extractItemInternal(0, stackInSlot.getCount(), false);
                handler.insertItemInternal(1, extracted, false);
            }
            return;
        }

        // 特殊物品
        int index = ModJsonRecipes.recipe_charger.getIndex(stackInSlot);
        if (index != -1) {
            ItemStack output = ModJsonRecipes.recipe_charger.getOutput(index);
            ItemStack input = ModJsonRecipes.recipe_charger.getInput(index);

            handler.extractItemInternal(0, input.getCount(), false);
            handler.insertItemInternal(1, output.copy(), false);
        }
    }


    static class ItemStackHandlerAutoCharger extends ItemStackHandlerBase {

        public ItemStackHandlerAutoCharger() {
            super(2);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot != 0) {
                return false;
            }
            IEnergyStorage energyStored = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (energyStored != null) {
                if (energyStored.canReceive() && energyStored.getEnergyStored() >= energyStored.getMaxEnergyStored()) {
                    return true;
                }
            }

            return ModJsonRecipes.recipe_charger.getIndex(stack) != -1;
        }
    }

}
