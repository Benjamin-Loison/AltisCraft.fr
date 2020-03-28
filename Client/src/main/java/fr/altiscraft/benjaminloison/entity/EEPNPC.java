package fr.altiscraft.benjaminloison.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EEPNPC implements IExtendedEntityProperties
{
    public final static String EXT_PROP_NAME = "EEPNPC";
    public ResourceLocation locationHat;
    public String texture;
    public ThreadDownloadImageData downloadImageHat;

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {}

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {}

    @Override
    public void init(Entity entity, World world)
    {}

    public static final EEPNPC get(Entity player)
    {
        return (EEPNPC)player.getExtendedProperties(EXT_PROP_NAME);
    }

    public ThreadDownloadImageData getTextureHat()
    {
        return downloadImageHat;
    }

    public ThreadDownloadImageData getDownloadImageHat(ResourceLocation resourceLocation, String uuid)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(resourceLocation);
        if(object == null)
        {
            object = new ThreadDownloadImageData((File)null, String.format(I18n.format("server.skin") + "%s.png", StringUtils.stripControlCodes(uuid)), null, new ImageBufferDownload()
            {
                @Override
                public BufferedImage parseUserSkin(BufferedImage image)
                {
                    return image;
                }
            });
            texturemanager.loadTexture(resourceLocation, (ITextureObject)object);
        }
        return (ThreadDownloadImageData)object;
    }

    public String getHatInfoUrl(String uuid)
    {
        return String.format(I18n.format("server.skin") + "%s.png", new Object[] {StringUtils.stripControlCodes(uuid)});
    }

    public ResourceLocation getLocationHat(String uuid)
    {
        return new ResourceLocation(AltisCraft.MODID, "texture/entity/skins/" + StringUtils.stripControlCodes(uuid));
    }
}
