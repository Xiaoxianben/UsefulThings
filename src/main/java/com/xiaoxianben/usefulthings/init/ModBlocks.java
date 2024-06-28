package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.blocks.BlockBase;
import com.xiaoxianben.usefulthings.blocks.BlockInfiniteWater;
import com.xiaoxianben.usefulthings.blocks.generator.BlockLingQiGenerator;
import com.xiaoxianben.usefulthings.blocks.machine.BlockLingQiCompression;
import com.xiaoxianben.usefulthings.blocks.machine.BlockLingQiGatherer;
import com.xiaoxianben.usefulthings.blocks.machine.BlockTimeWarp;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();


    public static final Block LingQi_block = new BlockBase("block_ling_qi", new Material(MapColor.IRON));
    /**
     * 方块-无限水
     */
    public static final BlockInfiniteWater blockInfiniteWater = new BlockInfiniteWater();
    public static final BlockLingQiGatherer BLOCK_LING_QI_GATHERER = new BlockLingQiGatherer();
    public static final BlockLingQiCompression BLOCK_LING_QI_COMPRESSION = new BlockLingQiCompression();
    public static final BlockLingQiGenerator BLOCK_LING_QI_GENERATOR = new BlockLingQiGenerator();
    public static final BlockTimeWarp BLOCK_TIME_WARP = new BlockTimeWarp();

    public static void initBlock() {
        MaterialLiquid MaterialReiki = (new MaterialLiquid(MapColor.WATER));

        Block reiki_block = new BlockFluidClassic(ModFluid.LingQi, MaterialReiki).setRegistryName(new ResourceLocation(ModInformation.MOD_ID, "LingQi")).setUnlocalizedName("ut-LingQi");
        Block reikiFluid_Block = new BlockFluidClassic(ModFluid.LingQi_fluid, MaterialReiki).setRegistryName(new ResourceLocation(ModInformation.MOD_ID, "LingQi_fluid")).setUnlocalizedName("ut-LingQi_fluid");

        BLOCKS.add(reiki_block);
        BLOCKS.add(reikiFluid_Block);
    }

    public static void initBlockRecipe() {
        ModRecipe.recipeBlockIngot(LingQi_block, ModItems.LingQi_ingot, "blockLingQi", "ingotLingQi");

        ModRecipe.registerBlockInfiniteWater(blockInfiniteWater);

        ModRecipe.recipeMachine(BLOCK_LING_QI_GATHERER, Items.CAULDRON);
        ModRecipe.recipeMachine(BLOCK_LING_QI_COMPRESSION, Item.getItemFromBlock(Blocks.PISTON));
        ModRecipe.recipeGenerator(BLOCK_LING_QI_GENERATOR, Item.getItemFromBlock(Blocks.FURNACE));

        ModRecipe.recipeLingQiMachine(BLOCK_TIME_WARP, Items.CLOCK);
    }
}
