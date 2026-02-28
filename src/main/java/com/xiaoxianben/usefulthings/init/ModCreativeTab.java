package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.init.modRegisterer.RegistererMinecraft;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ModCreativeTab extends CreativeTabs {

    public static final ModCreativeTab creative_tab_main = new ModCreativeTab(ModInformation.MOD_ID);

    public ModCreativeTab(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(RegistererMinecraft.abnormal_gem);
    }
}
