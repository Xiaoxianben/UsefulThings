package com.xiaoxianben.usefulthings.blocks;

import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.init.ModBlocks;
import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Objects;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material) {
        super(material);
        setUnlocalizedName("ut-" + name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.mod_creative_tab);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));

        setHarvestLevel("pickaxe", 1);
        this.setHardness(20.0F);
        setLightOpacity(1);
    }

    @Override
    public void registerModels() {
        UsefulThings.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
