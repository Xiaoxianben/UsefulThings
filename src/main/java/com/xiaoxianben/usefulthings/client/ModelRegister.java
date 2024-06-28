package com.xiaoxianben.usefulthings.client;

import com.xiaoxianben.usefulthings.api.IHasModel;
import com.xiaoxianben.usefulthings.init.ModBlocks;
import com.xiaoxianben.usefulthings.init.ModFluid;
import com.xiaoxianben.usefulthings.init.ModItems;
import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(
        modid = ModInformation.MOD_ID,
        value = Side.CLIENT
)
public class ModelRegister {

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(ModFluid.LingQi.getBlock(), new StateMapperBase() {
            @Nonnull
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(ModInformation.MOD_ID, "fluid"), "LingQi");
            }
        });
        ModelLoader.setCustomStateMapper(ModFluid.LingQi_fluid.getBlock(), new StateMapperBase() {
            @Nonnull
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(ModInformation.MOD_ID, "fluid"), "LingQi_fluid");
            }
        });

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }

    }

}
