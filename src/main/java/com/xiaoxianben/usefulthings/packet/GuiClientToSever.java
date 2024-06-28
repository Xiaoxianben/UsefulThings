package com.xiaoxianben.usefulthings.packet;

import com.xiaoxianben.usefulthings.api.ITextFieldChange;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class GuiClientToSever implements IMessage {
    public NBTTagCompound compound;

    // 默认构造函数（必需）
    public GuiClientToSever() {
    }

    public GuiClientToSever(NBTTagCompound compound) {
        this.compound = compound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<GuiClientToSever, IMessage> {
        @Override
        public IMessage onMessage(GuiClientToSever message, MessageContext ctx) {
            //判断是否为 接收端
            if (ctx.side == Side.SERVER) {
                Container container = ctx.getServerHandler().player.openContainer;
                if (container instanceof ITextFieldChange) {
                    int NBT = message.compound.getInteger("id");
                    String value = message.compound.getString("value");
                    ((ITextFieldChange) container).onTextFieldChange(NBT, value);
                }
            }
            return null;
        }
    }
}
