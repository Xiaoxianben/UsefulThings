package com.xiaoxianben.usefulthings.gui;

import com.xiaoxianben.usefulthings.TileEntity.generator.TEGeneratorLingQI;
import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineLingQICompression;
import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineLingQIGatherer;
import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineTimeWarp;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerCompression;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerGatherer;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerGenerator;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerTimeWarp;
import com.xiaoxianben.usefulthings.gui.gui.GUICompression;
import com.xiaoxianben.usefulthings.gui.gui.GUIGatherer;
import com.xiaoxianben.usefulthings.gui.gui.GUIGenerator;
import com.xiaoxianben.usefulthings.gui.gui.GUITimeWarp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class GUIHandler implements IGuiHandler {


    public final static int GUI_LingQIGatherer = 0;
    public final static int GUI_LingQICompression = 1;
    public final static int GUI_LingQIGenerator = 2;
    public final static int GUI_timeWarp = 3;


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        switch (ID) {
            case GUI_LingQIGatherer:
                return new ContainerGatherer(player, (TEMachineLingQIGatherer) world.getTileEntity(pos));

            case GUI_LingQICompression:
                return new ContainerCompression(player, (TEMachineLingQICompression) Objects.requireNonNull(world.getTileEntity(pos)));

            case GUI_LingQIGenerator:
                return new ContainerGenerator(player, (TEGeneratorLingQI) world.getTileEntity(pos));

            case GUI_timeWarp:
                return new ContainerTimeWarp(player, (TEMachineTimeWarp) world.getTileEntity(pos));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        switch (ID) {
            case GUI_LingQIGatherer:
                return new GUIGatherer(new ContainerGatherer(player, (TEMachineLingQIGatherer) world.getTileEntity(pos)));

            case GUI_LingQICompression:
                return new GUICompression(new ContainerCompression(player, (TEMachineLingQICompression) Objects.requireNonNull(world.getTileEntity(pos))));

            case GUI_LingQIGenerator:
                return new GUIGenerator(new ContainerGenerator(player, (TEGeneratorLingQI) world.getTileEntity(pos)));

            case GUI_timeWarp:
                return new GUITimeWarp(new ContainerTimeWarp(player, (TEMachineTimeWarp) world.getTileEntity(pos)));
        }
        return null;
    }
}
