package com.xiaoxianben.usefulthings.blocks;

import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.api.IHasModel;
import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material, ModCreativeTab creativeTab) {
        super(material);
        setUnlocalizedName("ut-" + name);
        setRegistryName(name);
        setCreativeTab(creativeTab);

        setHarvestLevel("pickaxe", 1);
        this.setHardness(10.0F);
    }

    @Override
    public void registerModels() {
        UsefulThings.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
