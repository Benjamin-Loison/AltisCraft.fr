package fr.altiscraft.benjaminloison.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import cpw.mods.fml.client.FMLClientHandler;
import fr.altiscraft.benjaminloison.api.FileAPI;
import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;

public class GuiCustomMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final Random rand = new Random();
    private float updateCounter;
    private GuiButton buttonResetDemo;
    private int panoramaTimer, fi0, fi1, fi2, fi3, fi4, fi5;
    private DynamicTexture viewportTexture;
    private final Object object = new Object();
    private String text, splashText, fi6, fi7, fi8;
    private static final ResourceLocation splashTexts = new ResourceLocation(AltisCraft.MODID, "Informations.txt"), minecraftTitleTextures = new ResourceLocation(AltisCraft.MODID, "textures/gui/AltisCraft.png");
    public static final String fi9 = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    int color = 0xFE2EF7, var = 200, myColor = 0xFFFFFF;
    private ResourceLocation r;
    private final ResourceLocation backGround = new ResourceLocation(AltisCraft.MODID, "textures/gui/Altis.png");

    public GuiCustomMainMenu()
    {
        fi7 = fi9;
        splashText = AltisCraft.NAME;
        BufferedReader bufferedreader = null;
        try
        {
            ArrayList arraylist = new ArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;
            while((s = bufferedreader.readLine()) != null)
            {
                s = s.trim();
                if(!s.isEmpty())
                    arraylist.add(s);
            }
            if(!arraylist.isEmpty())
                do
                    splashText = ((String)arraylist.get(rand.nextInt(arraylist.size())));
                while(splashText.hashCode() == 125780783);
            if(bufferedreader != null)
                try
                {
                    bufferedreader.close();
                }
                catch(Exception e)
                {}
            updateCounter = rand.nextFloat();
        }
        catch(Exception e)
        {}
        finally
        {
            if(bufferedreader != null)
                try
                {
                    bufferedreader.close();
                }
                catch(Exception e)
                {}
        }
        fi6 = "";
        if((!GLContext.getCapabilities().OpenGL20) && (!OpenGlHelper.func_153193_b()))
        {
            fi6 = I18n.format("title.oldgl1");
            fi7 = I18n.format("title.oldgl2");
            fi8 = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    public void updateScreen()
    {
        panoramaTimer += 1;
    }

    public void initGui()
    {
        GL11.glPushMatrix();
        GL11.glScaled(0.7, 0.7, 0);
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(I18n.format("authentification.resolver.server.url")).openStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null)
                text = inputLine;
            in.close();
            if(text.startsWith("!"))
            {
                text = text.replace("!", "");
                color = 0xFE1B00;
            }
        }
        catch(Exception e)
        {}
        GL11.glPopMatrix();
        viewportTexture = new DynamicTexture(256, 256);
        r = mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
        int i = height / 4 + 48;
        addSingleplayerMultiplayerButtons(i, 24);
        buttonList.add(new GuiButton(0, width / 2 - 100, i + 72 + 12, 98, 20, I18n.format("menu.options")));
        buttonList.add(new GuiButton(2, width / 2 + 2, i + 72 + 12, 98, 20, I18n.format("menu.quit")));
        Object object = this.object;
        synchronized(object)
        {
            fi2 = fontRendererObj.getStringWidth(fi6);
            fi1 = fontRendererObj.getStringWidth(fi7);
            int j = Math.max(fi2, fi1);
            fi3 = ((width - j) / 2);
            fi4 = (((GuiButton)buttonList.get(0)).yPosition - 24);
            fi5 = (fi3 + j);
            fi0 = (fi4 + 24);
        }
    }

    private void addSingleplayerMultiplayerButtons(int x, int y)
    {
        buttonList.add(new GuiButton(3, width / 2 - 100, x, 98, 20, I18n.format("website")));
        GuiButton ts = new GuiButton(4, width / 2 - 100, x + y * 2, I18n.format("teamspeak"));
        ts.xPosition = (width / 2 + 2);
        ts.width = 98;
        buttonList.add(ts);
        GuiButton shop = new GuiButton(5, width / 2 - 100, x + y * 2, I18n.format("shop"));
        shop.width = 98;
        buttonList.add(shop);
        GuiButton C = new GuiButton(6, width / 2 - 7, x + y * 2 + 22, "C");
        C.width = 15;
        C.height = 13;
        buttonList.add(C);
        buttonList.add(new GuiButton(10, width / 2 - 100, x + y * 1, EnumChatFormatting.DARK_AQUA + AltisCraft.NAME));
        buttonList.add(new GuiButton(11, width / 2 + 2, x + 0, 98, 20, "DarkRP"));
        buttonList.add(new GuiButton(12, width / 2 + 2, x + 105, 98, 20, "DayZCraft"));
        buttonList.add(new GuiButton(16, width / 2 - 100, x + 105, 98, 20, "Annihilation"));
        GuiButton WasteCraft = new GuiButton(14, width / 2 - 80, x + y * 2 + 22, "WasteCraft");
        WasteCraft.width = 65;
        WasteCraft.height = 13;
        buttonList.add(WasteCraft);
        GuiButton RealFaction = new GuiButton(15, width / 2 + 22, x + y * 2 + 22, "RealFaction");
        RealFaction.width = 65;
        RealFaction.height = 13;
        buttonList.add(RealFaction);
    }

    protected void actionPerformed(GuiButton btn)
    {
        if(btn.id == 0)
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        else if(btn.id == 2)
            mc.shutdown();
        else if(btn.id > 2 && btn.id < 6)
        {
            try
            {
                Class oclass = Class.forName("java.awt.Desktop");
                Method method = oclass.getMethod("getDesktop");
                if(btn.id == 3)
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(method.invoke(null), new Object[] {new URI(I18n.format("website.url"))});
                else if(btn.id == 4)
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(method.invoke(null), new Object[] {new URI(I18n.format("teamspeak.url", mc.getSession().getUsername().replace("_", "%20")))});
                else if(btn.id == 5)
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(method.invoke(null), new Object[] {new URI(I18n.format("shop.url"))});
            }
            catch(Throwable t)
            {}
        }
        else if(btn.id == 6)
            mc.getMinecraft().displayGuiScreen(new GuiChange());
        else if(btn.id == 10)
        {
            FMLClientHandler.instance().connectToServerAtStartup(I18n.format("altiscraft.ip"), FileAPI.configNumberInt("altiscraft.port"));
            ClientProxy.server = "AltisCraft";
            ClientProxy.rp = true;
            ClientProxy.safe = false;
        }
        else if(btn.id == 11)
        {
            FMLClientHandler.instance().connectToServerAtStartup(I18n.format("darkrp.ip"), FileAPI.configNumberInt("darkrp.port"));
            ClientProxy.server = "DarkRP";
            ClientProxy.rp = true;
            ClientProxy.safe = false;
        }
        else if(btn.id == 12)
        {
            FMLClientHandler.instance().connectToServerAtStartup(I18n.format("dayzcraft.ip"), FileAPI.configNumberInt("dayzcraft.port"));
            ClientProxy.server = "DayZCraft";
            ClientProxy.rp = true;
            ClientProxy.safe = false;
        }
        else if(btn.id == 14)
        {
            FMLClientHandler.instance().connectToServerAtStartup(I18n.format("wastecraft.ip"), FileAPI.configNumberInt("wastecraft.port"));
            ClientProxy.server = "WasteCraft";
            ClientProxy.rp = true;
            ClientProxy.safe = false;
        }
        else if(btn.id == 15)
        {
            FMLClientHandler.instance().connectToServerAtStartup(I18n.format("realfaction.ip"), FileAPI.configNumberInt("realfaction.port"));
            ClientProxy.server = "RealFaction";
            ClientProxy.rp = false;
            ClientProxy.safe = false;
        }
        else if(btn.id == 16)
        {
            FMLClientHandler.instance().connectToServerAtStartup(I18n.format("annihilation.ip"), FileAPI.configNumberInt("annihilation.port"));
            ClientProxy.server = "Annihilation";
            ClientProxy.rp = false;
            ClientProxy.safe = false;
        }
    }

    public void confirmClicked(boolean b, int id)
    {
        if((b) && (id == 12))
        {
            ISaveFormat isaveformat = mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            mc.displayGuiScreen(this);
        }
        else if(id == 13)
        {
            if(b)
                try
                {
                    Class oclass = Class.forName("java.awt.Desktop");
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(oclass.getMethod("getDesktop").invoke(null), new Object[] {new URI(fi8)});
                }
                catch(Throwable t)
                {}
            mc.displayGuiScreen(this);
        }
    }

    private void renderBackGround()
    {
        GL11.glViewport(0, 0, 256, 256);
        mc.getTextureManager().bindTexture(backGround);
        GL11.glDisable(3553);
        GL11.glEnable(3553);
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        tessellator.setColorRGBA_F(1, 1, 1, 1);
        int k = width, l = height;
        tessellator.addVertexWithUV(0, 0, zLevel, 0, 0);
        tessellator.addVertexWithUV(0, l, zLevel, 0, 1);
        tessellator.addVertexWithUV(k, l, zLevel, 1, 1);
        tessellator.addVertexWithUV(k, 0, zLevel, 1, 0);
        tessellator.draw();
    }

    public void drawScreen(int x, int y, float t)
    {
        GL11.glDisable(3008);
        renderBackGround();
        GL11.glEnable(3008);
        Tessellator tessellator = Tessellator.instance;
        short short1 = 274;
        int k = width / 2 - short1 / 2;
        byte b0 = 30;
        drawGradientRect(0, 0, width, height, -2130706433, 16777215);
        drawGradientRect(0, 0, width, height, 0, Integer.MIN_VALUE);
        mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GL11.glColor4f(1, 1, 1, 1);
        if(updateCounter < 1.0E-4D)
        {
            drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 99, 44);
            drawTexturedModalRect(k + 99, b0 + 0, 129, 0, 27, 44);
            drawTexturedModalRect(k + 99 + 26, b0 + 0, 126, 0, 3, 44);
            drawTexturedModalRect(k + 99 + 26 + 3, b0 + 0, 99, 0, 26, 44);
            drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
        }
        else
        {
            drawTexturedModalRect(k + 0, b0 + 0, 0, 0, 155, 44);
            drawTexturedModalRect(k + 155, b0 + 0, 0, 45, 155, 44);
        }
        tessellator.setColorOpaque_I(-1);
        GL11.glPushMatrix();
        GL11.glTranslatef(width / 2 + 90, 70, 0);
        GL11.glRotatef(-20, 0, 0, 1);
        float f1 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000 * (float)Math.PI * 2) * 0.1F);
        f1 = f1 * 100 / (fontRendererObj.getStringWidth(splashText) + 32);
        GL11.glScalef(f1, f1, f1);
        drawCenteredString(fontRendererObj, I18n.format(splashText), -10, -10, 65280);
        GL11.glPopMatrix();
        drawString(fontRendererObj, text, x + 5, y + 5, color);
        if((fi6 != null) && (fi6.length() > 0))
        {
            drawRect(fi3 - 2, fi4 - 2, fi5 + 2, fi0 - 1, 1428160512);
            drawString(fontRendererObj, fi6, fi3, fi4, -1);
            drawString(fontRendererObj, fi7, (width - fi1) / 2, ((GuiButton)buttonList.get(0)).yPosition - 12, -1);
        }
        mc.fontRenderer.drawString(I18n.format("connected.as") + mc.getSession().getUsername(), 5, 5, 0x000000);
        super.drawScreen(x, y, t);
    }

    protected void mouseClicked(int x, int y, int z)
    {
        super.mouseClicked(x, y, z);
        synchronized(object)
        {
            if((fi6.length() > 0) && (x >= fi3) && (x <= fi5) && (y >= fi4) && (y <= fi0))
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, fi8, 13, true);
                guiconfirmopenlink.func_146358_g();
                mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}