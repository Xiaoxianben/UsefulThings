package com.xiaoxianben.usefulthings.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

public class NBTUtil {
    public static NBTTagCompound getNbtCompoundFromItemStack(ItemStack stack) {
        NBTTagCompound nbtNew = new NBTTagCompound();
        NBTTagCompound nbtOld = new NBTTagCompound();

        nbtNew.setString("id", nbtOld.getString("id"));
        nbtNew.setInteger("Count", stack.getCount());
        nbtNew.setInteger("Damage", stack.getMetadata());

        if (stack.getTagCompound() != null) {
            nbtNew.setTag("tag", stack.getTagCompound());
        }


        if (nbtOld.hasKey("ForgeCaps")) {
            nbtNew.setTag("ForgeCaps", nbtOld.getTag("ForgeCaps"));
        }

        return nbtNew;
    }

    public static ItemStack getItemStackFromNbtCompound(NBTTagCompound nbt) {
        Item item = nbt.hasKey("id", Constants.NBT.TAG_STRING) ? Item.getByNameOrId(nbt.getString("id")) : Items.AIR;
        if (item == null) {
            return ItemStack.EMPTY;
        }

        NBTTagCompound capNbt = nbt.hasKey("ForgeCaps") ? nbt.getCompoundTag("ForgeCaps") : null;
        ItemStack itemStack = new ItemStack(item, nbt.getInteger("Count"), nbt.getInteger("Damage"), capNbt);

        if (nbt.hasKey("tag")) {
            itemStack.setTagCompound(nbt.getCompoundTag("tag"));
        }

        return itemStack;
    }

    public static NBTTagCompound getNbtCompoundFromItemStackHandler(ItemStackHandler handler) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                NBTTagCompound nbt = getNbtCompoundFromItemStack(stack);
                nbt.setInteger("Slot", i);
                list.appendTag(nbt);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", list);
        nbt.setInteger("Size", handler.getSlots());
        return nbt;
    }

    public static ItemStackHandler getItemStackHandlerFromNbtCompound(NBTTagCompound nbt, ItemStackHandler handler) {
        handler.setSize(nbt.getInteger("Size"));
        NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound nbtItem = list.getCompoundTagAt(i);
            int slot = nbtItem.getInteger("Slot");
            if (slot >= 0 && slot < handler.getSlots()) {
                handler.setStackInSlot(slot, getItemStackFromNbtCompound(nbtItem));
            }
        }
        return handler;
    }
}
