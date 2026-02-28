package com.xiaoxianben.usefulthings.item;

import com.xiaoxianben.usefulthings.blocks.type.EnumAutoMachineLevel;
import com.xiaoxianben.usefulthings.blocks.type.EnumTypeMachineCommon;
import com.xiaoxianben.usefulthings.item.itemBlock.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ItemBlockAutoMachine extends ItemBlockBase {
    public ItemBlockAutoMachine(Block block) {
        super(block);

        setHasSubtypes(true);
        //将最大损害值设定为0，避免一些奇妙的“修复物品的设备”产生的刷物品 Bug。
        setMaxDamage(0);
        //防止被修复。避免一些奇妙的“修复物品的设备”强行修复该物品。
        setNoRepair();
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + EnumTypeMachineCommon.getType(stack.getMetadata()).id;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return super.getItemStackDisplayName(stack) + I18n.format("level.ut-"+ EnumAutoMachineLevel.getLevel(stack).levelName+".name");
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubItems(tab, items);
    }
}
