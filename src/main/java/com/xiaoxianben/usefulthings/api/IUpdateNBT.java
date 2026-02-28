package com.xiaoxianben.usefulthings.api;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * 用于 {@link net.minecraft.tileentity.TileEntity}
 */
public interface IUpdateNBT {
    /**
     * 获取需要发送的NBT, 应该只被用于网络更行
     *
     * @return 需要发送的网络NBT
     */
    @Nonnull
    NBTTagCompound getNetworkNBT();

    /**
     * 处理网络NBT
     *
     * @param nbt 网络NBT
     */
    void handlerNetworkNBT(NBTTagCompound nbt);

    /**
     * 获取Capability NBT, 应该只被用于Capability更新
     *
     * @return 需要发送的Capability NBT
     */
    @Nonnull
    NBTTagCompound getCapabilityNBT();

    /**
     * 处理Capability NBT
     *
     * @param nbt Capability NBT
     */
    void handlerCapabilityNBT(NBTTagCompound nbt);

    /**
     * 获取默认NBT, 应该只被用于 {@link net.minecraft.tileentity.TileEntity#writeToNBT(NBTTagCompound)}。
     *
     * @return 默认NBT，不用在网络中实时更新的数据
     */
    @Nonnull
    NBTTagCompound getDefaultNBT();

    /**
     * 处理默认NBT， 应该只被用于 {@link net.minecraft.tileentity.TileEntity#readFromNBT(NBTTagCompound)}。
     *
     * @param nbt 默认NBT
     */
    void handlerDefaultNBT(NBTTagCompound nbt);
}
