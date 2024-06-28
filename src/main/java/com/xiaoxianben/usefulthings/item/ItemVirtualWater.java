package com.xiaoxianben.usefulthings.item;

import com.xiaoxianben.usefulthings.item.ItemFluidHandler.FluidHandlerVirtualWater;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class ItemVirtualWater extends ItemBase {
    public ItemVirtualWater() {
        super("virtual_water");
        this.setMaxStackSize(1);
    }


    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {

        tooltip.add(I18n.format("item.lqtech-virtual_water.information"));

        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null && nbt.hasKey("mode")) {
            tooltip.add(I18n.format("item.lqtech-virtual_water.mode-" + nbt.getBoolean("mode")));
        } else {
            tooltip.add(I18n.format("item.lqtech-virtual_water.mode-false"));
        }

    }


    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack handItemStack = playerIn.getHeldItem(handIn);
        NBTTagCompound itemNbt = handItemStack.getTagCompound();

        // 这里实现虚拟水的切换模式逻辑
        if (itemNbt == null || !itemNbt.hasKey("mode")) {
            itemNbt = itemNbt == null ? new NBTTagCompound() : itemNbt;
            itemNbt.setBoolean("mode", false);
            handItemStack.setTagCompound(itemNbt);
        }
        if (playerIn.isSneaking()) {
            itemNbt.setBoolean("mode", !itemNbt.getBoolean("mode"));
            handItemStack.setTagCompound(itemNbt);
            return new ActionResult<>(EnumActionResult.SUCCESS, handItemStack);
        }

        return new ActionResult<>(EnumActionResult.PASS, handItemStack);
    }

    @ParametersAreNonnullByDefault
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // 这里实现虚拟水的使用逻辑，例如将虚拟水放置在指定位置
        boolean isSuccess = false;
        if (Objects.requireNonNull(player.getHeldItem(hand).getTagCompound()).getBoolean("mode")) {
            BlockPos upPos = pos.up();
            IBlockState blockState = worldIn.getBlockState(upPos);
            Block block = blockState.getBlock();

            if (!block.onBlockActivated(worldIn, upPos, blockState, player, hand, facing, hitX, hitY, hitZ) && block.canPlaceBlockAt(worldIn, upPos)) {
                isSuccess = worldIn.setBlockState(upPos, FluidRegistry.WATER.getBlock().getDefaultState());
                worldIn.notifyBlockUpdate(upPos, blockState, blockState, 3);
            }
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity != null && tileentity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
                isSuccess = FluidUtil.interactWithFluidHandler(player, hand, worldIn, pos, facing);
            }
        }
        if (isSuccess) return EnumActionResult.SUCCESS;
        return EnumActionResult.PASS;
    }

    @ParametersAreNonnullByDefault
    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.NBTTagCompound nbt) {
        return new FluidHandlerVirtualWater(stack, Integer.MAX_VALUE);
    }
}
