package com.xiaoxianben.usefulthings.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ModCreativeTab extends CreativeTabs {

    public static final ModCreativeTab mod_creative_tab = new ModCreativeTab("mod_creative_tab");

    public ModCreativeTab(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return ModItems.ITEMS.get(0).getDefaultInstance();
    }
}
