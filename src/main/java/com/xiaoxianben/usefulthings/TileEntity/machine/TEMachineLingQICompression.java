package com.xiaoxianben.usefulthings.TileEntity.machine;

import com.xiaoxianben.usefulthings.TileEntity.itemStackHandler.ItemStackHandlerOutput;
import com.xiaoxianben.usefulthings.fluids.FluidTankBase;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.recipe.ItemOrFluid;
import com.xiaoxianben.usefulthings.recipe.Recipes;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class TEMachineLingQICompression extends TEMachineBase implements ITickable {


    public Recipes<FluidStack, ItemOrFluid> recipes = RecipesList.lingQiCompression;

    /**
     * 输入的流体容器, 5000B
     */
    public FluidTank inputFluidTank = new FluidTankBase(10 * 1000000) {
        public boolean canFillFluidType(FluidStack fluid) {
            return recipes.getInputs().stream().map(FluidStack::getFluid).anyMatch(fluid1 -> fluid1 == fluid.getFluid());
        }
    };
    /**
     * 输出的流体容器, 50B
     */
    public FluidTank outputFluidTank = new FluidTankBase(1000000);

    public ItemStackHandlerOutput outputSlot = new ItemStackHandlerOutput();


    public TEMachineLingQICompression() {
        super();
        this.inputFluidTank.setCanDrain(false);
        this.outputFluidTank.setCanFill(false);

    }


    @Override
    public void updateState() {
        this.isActive = false;
        if (this.inputFluidTank.getFluidAmount() <= 0) return;

        if (this.runMachine(true) > 0) {
            this.isActive = true;
            this.runMachine(false);
        }

    }

    /**
     * 返回接受的数量
     */
    protected int runMachine(boolean simulate) {

        int acceptNumber = 0;
        int runNum = 0;

        FluidStack inputFluidType;

        do {
            inputFluidType = this.recipes.getInputs()
                    .stream()
                    .filter(fluidStack -> Objects.requireNonNull(this.inputFluidTank.getFluid()).containsFluid(fluidStack))
                    .findFirst()
                    .orElse(null)
            ;

            ItemOrFluid output = this.recipes.getOutput(inputFluidType);
            if (output.get() instanceof FluidStack) {
                acceptNumber += this.outputFluidTank.fillInternal(((FluidStack) output.get()).copy(), !simulate);
            } else {
                ItemStack itemStack = ((ItemStack) output.get()).copy();
                acceptNumber += itemStack.getCount() - this.outputSlot.insertItemPrivate(0, itemStack, simulate).getCount();
            }

            if (!simulate) {
                if (runNum > 0) this.ModifyEnergy(-this.recipes.getEnergyDeplete(inputFluidType));
                this.inputFluidTank.drainInternal(inputFluidType, true);
            } else {
                break;
            }
            ++runNum;
        } while (inputFluidType != null && this.energy >= this.recipes.getEnergyDeplete(inputFluidType) && this.itemComponentHandler.getComponent(ModItems.component_machine) > 0);

        return acceptNumber;
    }


    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound NBT = super.writeToNBT(compound);

        NBTTagCompound fluidNBT = new NBTTagCompound();
        fluidNBT.setTag("inputFluidTank", this.inputFluidTank.writeToNBT(new NBTTagCompound()));
        fluidNBT.setTag("outputFluidTank", this.outputFluidTank.writeToNBT(new NBTTagCompound()));
        NBT.setTag("fluidTank", fluidNBT);

        NBT.setTag("outputSlot", this.outputSlot.serializeNBT());


        return NBT;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.outputSlot.deserializeNBT(compound.getCompoundTag("outputSlot"));

        NBTTagCompound fluidNBT = compound.getCompoundTag("fluidTank");
        this.inputFluidTank.readFromNBT(fluidNBT.getCompoundTag("inputFluidTank"));
        this.outputFluidTank.readFromNBT(fluidNBT.getCompoundTag("outputFluidTank"));
    }


    @ParametersAreNonnullByDefault
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new FluidHandlerConcatenate(this.inputFluidTank, this.outputFluidTank));
        }
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new CombinedInvWrapper(this.outputSlot, (IItemHandlerModifiable) super.getCapability(capability, facing)));
        }

        return super.getCapability(capability, facing);
    }
}
