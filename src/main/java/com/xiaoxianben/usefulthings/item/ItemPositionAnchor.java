package com.xiaoxianben.usefulthings.item;

import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPositionAnchor extends ItemBase {
    public static final String key_position = "position";

    public ItemPositionAnchor() {
        super("position_anchor", ModCreativeTab.creative_tab_main);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        BlockPos position = playerIn.getPosition();

        if (playerIn.isSneaking()) {
            heldItem.setTagInfo(key_position, new NBTTagIntArray(new int[]{position.getX(), position.getY(), position.getZ()}));
            return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        }

        return new ActionResult<>(EnumActionResult.PASS, heldItem);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }

        ItemStack heldItem = player.getHeldItem(hand);
        heldItem.setTagInfo(key_position, new NBTTagIntArray(new int[]{pos.getX(), pos.getY(), pos.getZ()}));

        return EnumActionResult.SUCCESS;
    }
}
