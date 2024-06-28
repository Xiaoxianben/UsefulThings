package com.xiaoxianben.usefulthings.gui.gui;

import com.xiaoxianben.usefulthings.TileEntity.machine.TEMachineTimeWarp;
import com.xiaoxianben.usefulthings.UsefulThings;
import com.xiaoxianben.usefulthings.gui.contrainer.ContainerTimeWarp;
import com.xiaoxianben.usefulthings.gui.gui.responder.IntResponder;
import com.xiaoxianben.usefulthings.packet.GuiClientToSever;
import com.xiaoxianben.usefulthings.recipe.RecipesList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;

public class GUITimeWarp extends GUIBase {

    public ContainerTimeWarp con;
    public ArrayList<GuiTextField> textField = new ArrayList<>();
    public int[] starts = {37, 15};
    public int FontSize = 8;


    public GUITimeWarp(ContainerTimeWarp con) {
        super(con, 3);
        this.con = con;
    }


    protected GuiTextField createTextField(int id, int x, int y, int width, int height, String inputText) {
        GuiTextField guiTextField = new GuiTextField(id, Minecraft.getMinecraft().fontRenderer, x, y, width, height);

        guiTextField.setEnableBackgroundDrawing(true);
        guiTextField.setTextColor(16777215);
        guiTextField.setMaxStringLength(width);
        guiTextField.setFocused(false);
        guiTextField.setText(inputText);
        guiTextField.setGuiResponder(new IntResponder() {
            @Override
            public void setEntryValue(int id, @Nonnull String value) {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("id", id);
                nbt.setString("value", value.isEmpty() ? "0" : value);

                UsefulThings.getNetwork().sendToServer(new GuiClientToSever(nbt));
            }
        });
        guiTextField.setValidator((text) -> {
            try {
                if (text.isEmpty()) return true;
                Integer.parseInt(text);
                if (id == con.tileEntity.scope.length) {
                    if (Integer.parseInt(text) > RecipesList.timeWarp.getInputs().size()) {
                        text = String.valueOf(RecipesList.timeWarp.getInputs().size());
                    } else if (Integer.parseInt(text) < 0) {
                        text = "0";
                    }
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        return guiTextField;
    }


    @Override
    protected void drawTextured() {
        TEMachineTimeWarp tileEntity = con.tileEntity;

        this.drawTextureRect(tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL(), 7, 4, 176, 0, 20, 72);

        for (GuiTextField guiTextField : this.textField) {
            guiTextField.drawTextBox();
        }
    }

    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        TEMachineTimeWarp tileEntity = con.tileEntity;

        // 绘制鼠标矩形
        drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, String.format("FE:\n%d/%d", tileEntity.getEnergyStoredL(), tileEntity.getMaxEnergyStoredL()));
    }

    @Override
    public void initGui() {
        super.initGui();
        for (int i = 0; i < con.tileEntity.scope.length; i++) {
            textField.add(this.createTextField(i, this.guiLeft + starts[0] + FontSize, this.guiTop + starts[1] + (FontSize + 2) * i, 100, FontSize, String.valueOf(con.tileEntity.scope[i])));
        }
        textField.add(this.createTextField(con.tileEntity.scope.length, this.guiLeft + starts[0] + FontSize, this.guiTop + starts[1] + (2 + FontSize) * con.tileEntity.scope.length, 100, FontSize, String.valueOf(con.tileEntity.multiplier)));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        for (GuiTextField guiTextField : this.textField) {
            guiTextField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (GuiTextField guiTextField : this.textField) {
            guiTextField.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

}
