package com.xiaoxianben.usefulthings.TileEntity;

import com.xiaoxianben.usefulthings.api.IUpdateNBT;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class TEBase extends TileEntity implements ITickable, IUpdateNBT {
    @Override
    public void update() {
        if (this.getWorld().isRemote) return;

        this.updateState();

/*a
        NBTTagCompound updateNBT = new NBTTagCompound();

        NBTTagCompound blockPos = new NBTTagCompound();
        blockPos.setInteger("x", this.getPos().getX());
        blockPos.setInteger("y", this.getPos().getY());
        blockPos.setInteger("z", this.getPos().getZ());

        updateNBT.setTag("blockPos", blockPos);
        updateNBT.setTag("updateNBT", this.getUpdateNBT());
        updateNBT.setTag("NBT", this.writeToNBT(new NBTTagCompound()));
        // 发送更新标签
        UsefulThings.getNetwork().sendToAll(new PacketConsciousness(updateNBT));
        */
        SPacketUpdateTileEntity packet = this.getUpdatePacket();
        // 获取当前正在“追踪”目标 TileEntity 所在区块的玩家。
        // 之所以这么做，是因为在逻辑服务器上，不是所有的玩家都需要获得某个 TileEntity 更新的信息。
        // 比方说，有一个玩家和需要同步的 TileEntity 之间差了八千方块，或者压根儿就不在同一个维度里。
        // 这个时候就没有必要同步数据——强行同步数据实际上也没有什么用，因为大多数时候这样的操作都应会被
        // World.isBlockLoaded（func_175667_e）的检查拦截下来，避免意外在逻辑客户端上加载多余的区块。
        PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
        if (trackingEntry != null) {
            for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
                player.connection.sendPacket(packet);
            }
        }
    }

    /**
     * 在 服务端 更新方块实体。
     */
    public abstract void updateState();

    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @Override
    @Nonnull
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound updateNBT = new NBTTagCompound();

        updateNBT.setTag("updateNBT", this.getUpdateNBT());
        updateNBT.setTag("NBT", this.writeToNBT(new NBTTagCompound()));
        // 发送更新标签
        return new SPacketUpdateTileEntity(this.getPos(), 1, updateNBT);
    }

    @Override
    public void onDataPacket(@Nonnull net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound().getCompoundTag("NBT"));
        this.updateNBT(pkt.getNbtCompound().getCompoundTag("updateNBT"));
    }

}
