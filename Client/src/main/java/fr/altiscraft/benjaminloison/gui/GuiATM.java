package fr.altiscraft.benjaminloison.gui;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import fr.altiscraft.benjaminloison.api.FileAPI;
import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.entity.EEP;
import fr.altiscraft.benjaminloison.packets.PacketMoneyDownGang;
import fr.altiscraft.benjaminloison.packets.PacketTransfer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class GuiATM extends GuiScreen
{
    int guiWidth = 140, guiHeight = 220, x, y, z;
    private GuiTextField amountField, name;
    private EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    private EEP props = EEP.get(player);

    public GuiATM(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void drawScreen(int x, int y, float tick)
    {
        int guiX = (width - guiWidth) / 2, guiY = (height - guiHeight) / 2;
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(new ResourceLocation(AltisCraft.MODID, "textures/gui/Background ATM.png"));
        drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
        fontRendererObj.drawString(I18n.format("title.atm"), guiX + 2, guiY + 5, 0xFFFFFF);
        fontRendererObj.drawString(I18n.format("money.amount", FileAPI.number(props.money)), guiX + 48, guiY + 25, 0xFFFFFF);
        fontRendererObj.drawString(I18n.format("money.amount", FileAPI.number(props.cash)), guiX + 48, guiY + 42, 0xFFFFFF);
        amountField.drawTextBox();
        name.drawTextBox();
        super.drawScreen(x, y, tick);
    }

    @Override
    public void initGui()
    {
        int guiX = (width - guiWidth) / 2, guiY = (height - guiHeight) / 2;
        buttonList.clear();
        buttonList.add(new CustomBlackButton(0, guiX + 5, guiY + 190 + 5, I18n.format("close"), 50, 20));
        buttonList.add(new CustomOrangeButton(1, guiX + 20, guiY + 72 + 5, I18n.format("take"), 98, 20));
        buttonList.add(new CustomOrangeButton(2, guiX + 20, guiY + 95 + 5, I18n.format("deposit"), 98, 20));
        buttonList.add(new CustomOrangeButton(3, guiX + 20, guiY + 138 + 5, I18n.format("transfer"), 98, 20));
        if(!props.gang.equals(""))
            buttonList.add(new CustomOrangeButton(4, guiX + 5, guiY + 165 + 5, I18n.format("deposit.for.gang"), 130, 20));
        amountField = new GuiTextField(fontRendererObj, width / 2 - 50, height / 2 - 55, 98, 20);
        amountField.setMaxStringLength(FileAPI.configNumberInt("amount.maximum.length"));
        amountField.setText(I18n.format("amount"));
        amountField.setFocused(true);
        name = new GuiTextField(fontRendererObj, width / 2 - 50, height / 2 + 12, 98, 20);
        name.setMaxStringLength(23);
        name.setText(I18n.format("name"));
        super.initGui();
    }

    protected void keyTyped(char c, int i)
    {
        super.keyTyped(c, i);
        amountField.textboxKeyTyped(c, i);
        name.textboxKeyTyped(c, i);
    }

    protected void mouseClicked(int x, int y, int btn)
    {
        super.mouseClicked(x, y, btn);
        amountField.mouseClicked(x, y, btn);
        name.mouseClicked(x, y, btn);
    }

    @Override
    protected void actionPerformed(GuiButton btn)
    {
        if(btn.id == 0)
            Minecraft.getMinecraft().displayGuiScreen(null);
        else
        {
            if(amountField.getText().equals("") || !StringUtils.isNumeric(amountField.getText()))
            {
                player.addChatMessage(new ChatComponentText(I18n.format("amount.invalid")));
                return;
            }
            long amount = Long.parseLong(amountField.getText());
            if(!props.sufficientMoney(amount))
            {
                player.addChatMessage(new ChatComponentText(I18n.format("you.have.not.enough.money")));
                return;
            }
            if(btn.id == 1)
                AltisCraft.instance.network.sendToServer(new PacketTransfer(true, false, amount));
            else if(btn.id == 2)
                AltisCraft.instance.network.sendToServer(new PacketTransfer(true, true, amount));
            else if(btn.id == 3)
                if(!name.getText().equals("") && name.getText().length() > 2)
                    AltisCraft.instance.network.sendToServer(new PacketTransfer(amount, name.getText()));
                else
                    player.addChatMessage(new ChatComponentText(I18n.format("enter.a.name.which.is.minimum.of.two.characters")));
            else if(btn.id == 4)
                AltisCraft.instance.network.sendToServer(new PacketMoneyDownGang(x, y, z, amount, props.gang));
        }
    }
}
