package com.xiaoxianben.usefulthings.blocks;

import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.api.IHasItemNBT;
import com.xiaoxianben.usefulthings.blocks.type.EnumTypeMachineCommon;
import com.xiaoxianben.usefulthings.gui.GUIHandler;
import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import com.xiaoxianben.usefulthings.tileEntity.auto.TEAutoMachine;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockAutoMachine extends BlockTEBase {

    public static final PropertyInteger type_auto_craft = PropertyInteger.create("type", 0, EnumTypeMachineCommon.values().length - 1);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockAutoMachine() {
        super("auto_machine", ModCreativeTab.creative_tab_main);
    }


    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, type_auto_craft);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState defaultState = getDefaultState();
        return defaultState.withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof TEAutoMachine) {
            if (((TEAutoMachine) tileEntity).typeCraft != null) {
                state = state.withProperty(type_auto_craft, ((TEAutoMachine) tileEntity).typeCraft.id);
            }
        }

        return state;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        state = state.withProperty(FACING, facing);

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TEAutoMachine) {
            EnumTypeMachineCommon type = EnumTypeMachineCommon.getType(stack.getMetadata());

            ((TEAutoMachine) tileEntity).typeCraft = type;
            ((TEAutoMachine) tileEntity).updateContainer(type);
            state = state.withProperty(type_auto_craft, type.id);
        }

        NBTTagCompound subCompound = stack.getSubCompound(IHasItemNBT.Key);
        if (tileEntity instanceof IHasItemNBT && subCompound != null) {
            ((IHasItemNBT) tileEntity).HandleItemNBT(subCompound);
        }

        worldIn.setBlockState(pos, state);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (Integer value : type_auto_craft.getAllowedValues()) {
            int meta = EnumTypeMachineCommon.values()[value].id;
            items.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        TileEntity tileEntity = world.getTileEntity(pos);


        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
                ItemStack itemStack = new ItemStack(item, 1, this.damageDropped(state));
                if (tileEntity instanceof IHasItemNBT) {
                    itemStack.setTagInfo(IHasItemNBT.Key, ((IHasItemNBT) tileEntity).getItemNBT());
                }
                drops.add(itemStack);
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(type_auto_craft);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(UsefulThings.instance, GUIHandler.GUI_autoMachine, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEAutoMachine();
    }

    @Override
    public void registerModels() {
        Item itemFromBlock = Item.getItemFromBlock(this);

        for (Integer value : type_auto_craft.getAllowedValues()) {
            int meta = EnumTypeMachineCommon.values()[value].id;
            UsefulThings.proxy.registerItemRenderer(itemFromBlock, meta, "inventory");
        }
    }
}
