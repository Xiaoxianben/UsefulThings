package com.xiaoxianben.usefulthings.TileEntity.machine;

import com.xiaoxianben.usefulthings.TileEntity.itemStackHandler.ItemComponentHandler;
import com.xiaoxianben.usefulthings.config.ConfigValue;
import com.xiaoxianben.usefulthings.recipe.Recipes;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.BlockFluidBase;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.lang.model.type.NullType;

public class TEMachineTimeWarp extends TEMachineBase {


    public BlockPos minBlockPos = this.getPos();
    public BlockPos maxBlockPos = this.getPos();
    public int[] scope = new int[]{0, 0, 0};
    public int[] scopeNow = new int[]{0, 0, 0};
    public int multiplier = 0;
    Recipes<Integer, NullType> recipe = RecipesList.timeWarp;


    public TEMachineTimeWarp() {
        super();
        this.itemComponentHandler = new ItemComponentHandler(0, ItemComponentHandler.empty);
        this.isActive = true;
    }


    @Override
    public void updateState() {
        if (!this.isActive || this.multiplier <= 0) return;
        this.updateBlockPosInScope();

        int energyDe = this.recipe.getEnergyDeplete(this.multiplier - 1);
        if (this.getEnergyStoredL() < energyDe) {
            return;
        }
        this.ModifyEnergy(-energyDe);
        // 更新方块状态
        for (BlockPos blockPos : BlockPos.getAllInBox(minBlockPos, maxBlockPos)) {
            this.TickBlock(blockPos);
        }
    }

    public void updateBlockPosInScope() {
        if (scopeNow[0] != scope[0] || scopeNow[1] != scope[1] || scopeNow[2] != scope[2]) {
            minBlockPos = this.getPos().add(-scope[0], -scope[1], -scope[2]);
            maxBlockPos = this.getPos().add(scope[0], scope[1], scope[2]);
        }
    }

    public void TickBlock(BlockPos pos) {
        IBlockState state = this.world.getBlockState(pos);
        Block block = state.getBlock();

        if (block.isAir(state, this.getWorld(), pos) || block instanceof BlockLiquid || block instanceof BlockFluidBase || ConfigValue.blackList.contains(block)) {
            return;
        }
        if (block.getTickRandomly()) {
            hastenBlock(() -> block.updateTick(this.getWorld(), pos, state, this.getWorld().rand));

        } else if (block instanceof IGrowable) {
            hastenBlock(() -> ((IGrowable) block).grow(this.getWorld(), this.getWorld().rand, pos, state));
        }

        TileEntity tileEntity = this.world.getTileEntity(pos);
        if (tileEntity instanceof ITickable) {
            hastenBlock(() -> ((ITickable) tileEntity).update());
        }
    }

    public void hastenBlock(Runnable action) {
        for (int i = 0; i < this.multiplier; i++) {
            action.run();
        }
    }

    // updateNBT
    public NBTTagCompound getUpdateNBT() {
        return new NBTTagCompound();
    }

    public void updateNBT(NBTTagCompound nbt) {
    }

    // NBT
    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound NBT = super.writeToNBT(compound);

        NBT.setIntArray("scope", scope);
        NBT.setInteger("multiplier", multiplier);
        NBT.setBoolean("isActive", isActive);

        return NBT;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        scope = compound.getIntArray("scope");
        multiplier = compound.getInteger("multiplier");
        isActive = compound.getBoolean("isActive");
    }

}
