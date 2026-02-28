package com.xiaoxianben.usefulthings.blocks;

import com.xiaoxianben.usefulthings.init.ModCreativeTab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class BlockTEBase extends BlockBase implements ITileEntityProvider {
    public BlockTEBase(String name, ModCreativeTab creativeTab) {
        super(name, Material.IRON, creativeTab);
    }

    public BlockTEBase(String name, Material material, ModCreativeTab creativeTab) {
        super(name, material, creativeTab);
    }
}
