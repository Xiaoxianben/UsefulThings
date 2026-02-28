package com.xiaoxianben.usefulthings.tileEntity;

import com.xiaoxianben.usefulthings.api.IUpdateNBT;
import com.xiaoxianben.usefulthings.config.ConfigLoader;
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
        if (this.getWorld().isRemote) {
            this.updateInClient();
            return;
        }

        this.updateInSever();
    }


    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @Override
    @Nonnull
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound updateNBT = new NBTTagCompound();

        updateNBT.setTag("network", this.getNetworkNBT());
        updateNBT.setTag("capability", this.getCapabilityNBT());

        return new SPacketUpdateTileEntity(this.getPos(), 1, updateNBT);
    }

    @Override
    public void onDataPacket(@Nonnull net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);

        NBTTagCompound compound = pkt.getNbtCompound();

        this.handlerNetworkNBT(compound.getCompoundTag("network"));
        this.handlerCapabilityNBT(compound.getCompoundTag("capability"));
    }

    // ---- nbt
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("capability", this.getCapabilityNBT());
        compound.setTag("default", this.getDefaultNBT());

        return compound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.handlerCapabilityNBT(compound.getCompoundTag("capability"));
        this.handlerDefaultNBT(compound.getCompoundTag("default"));
    }
    // ----

    public abstract void updateInSever();

    public abstract void updateInClient();

    public void sendPacketToClientFromSever() {
        if (this.world.isRemote) {
            String e = String.format("Contracts should not be sent from client to client. tile entry: %s, pos: %s", this, this.pos);
            ConfigLoader.logger().error(new Exception(e));
            return;
        }
        SPacketUpdateTileEntity packet = this.getUpdatePacket();
        PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);

        if (trackingEntry != null) {
            for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
                player.connection.sendPacket(packet);
            }
        }
    }
}
