package com.xiaoxianben.usefulthings.init.modRegisterer;

import com.xiaoxianben.usefulthings.api.IModRegisterer;
import com.xiaoxianben.usefulthings.blocks.BlockAltar;
import com.xiaoxianben.usefulthings.blocks.BlockAutoMachine;
import com.xiaoxianben.usefulthings.init.ModRegisterer;
import com.xiaoxianben.usefulthings.item.ItemBlockAutoMachine;
import com.xiaoxianben.usefulthings.item.ItemBottleLing;
import com.xiaoxianben.usefulthings.item.ItemPositionAnchor;
import com.xiaoxianben.usefulthings.item.abnormal.ItemAbnormalDust;
import com.xiaoxianben.usefulthings.item.abnormal.ItemAbnormalGem;
import com.xiaoxianben.usefulthings.item.abnormal.ItemAbnormalIngot;
import com.xiaoxianben.usefulthings.item.itemBlock.ItemBlockBase;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class RegistererMinecraft implements IModRegisterer {
    public static ItemAbnormalIngot abnormal_ingot;
    public static ItemAbnormalDust abnormal_dust;
    public static ItemAbnormalGem abnormal_gem;
    public static ItemBottleLing bottle_ling;
    public static ItemPositionAnchor position_anchor;

    public static BlockAltar altar;

    public static BlockAutoMachine auto_machine;

    public static Fluid chemical;
    public static Fluid chemical_waste;
    public static Fluid chemical_source;
    public static Fluid washes;


    @Override
    public void preInit() {
        abnormal_ingot = addItem(new ItemAbnormalIngot());
        abnormal_dust = addItem(new ItemAbnormalDust());
        abnormal_gem = addItem(new ItemAbnormalGem());

        bottle_ling = addItem(new ItemBottleLing());
        position_anchor = addItem(new ItemPositionAnchor());

        altar = addBlock(new BlockAltar());
        auto_machine = addBlock(new BlockAutoMachine());

        addItem(new ItemBlockBase(altar));
        addItem(new ItemBlockAutoMachine(auto_machine));

        // fluid
        chemical = addFluid("chemical", false);
        chemical_waste = addFluid("chemical_waste", false);
        chemical_source = addFluid("chemical_source", false);
        washes = addFluid("washes", false);
    }

    @Override
    public void initFirst() {

    }

    @Override
    public void initEnd() {

    }

    @Override
    public void postInit() {
    }

    @Override
    public String getModId() {
        return "minecraft";
    }

    public <T extends Item> T addItem(T item) {
        ModRegisterer.ITEMS.add(item);
        return item;
    }

    public <T extends Block> T addBlock(T block) {
        ModRegisterer.BLOCKS.add(block);
        return block;
    }

    public Fluid addFluid(String name, boolean isGaseous) {
        Fluid fluid = new Fluid(name, new ResourceLocation(ModInformation.MOD_ID+":fluids/"+name+"_still"), new ResourceLocation(ModInformation.MOD_ID+":fluids/"+name+"_flow"));
        fluid.setGaseous(isGaseous);
        FluidRegistry.addBucketForFluid(fluid);

        BlockFluidClassic blockFluidChemical = (BlockFluidClassic) new BlockFluidClassic(fluid, Material.WATER)
                .setRegistryName(ModInformation.MOD_ID, fluid.getName())
                .setUnlocalizedName(ModInformation.MOD_ID + "." + fluid.getName());

        return FluidRegistry.getFluid(name);
    }
}
