package com.xiaoxianben.usefulthings.init;

import com.xiaoxianben.usefulthings.util.ModInformation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModFluid {

    public static final List<Fluid> FLUIDS = new ArrayList<>();

    /**
     * 灵气
     */
    public static Fluid LingQi;
    /**
     * 液态灵气
     */
    public static Fluid LingQi_fluid;

    public static void initFluid() {
        LingQi = new Fluid("lingqi", new ResourceLocation(ModInformation.MOD_ID, "fluids/lingqi_still"), new ResourceLocation(ModInformation.MOD_ID, "fluids/lingqi_flow"));
        LingQi.setTemperature(0).setGaseous(true).setLuminosity(8).setDensity(10);

        LingQi_fluid = new Fluid("lingqi_fluid", new ResourceLocation(ModInformation.MOD_ID, "fluids/lingqi_fluid_still"), new ResourceLocation(ModInformation.MOD_ID, "fluids/lingqi_fluid_flow"));
        LingQi_fluid.setTemperature(0).setGaseous(false).setLuminosity(8).setDensity(1000);

        FLUIDS.add(LingQi);
        FLUIDS.add(LingQi_fluid);

        for (Fluid fluid : FLUIDS) {
            FluidRegistry.addBucketForFluid(fluid);
        }
    }
}
