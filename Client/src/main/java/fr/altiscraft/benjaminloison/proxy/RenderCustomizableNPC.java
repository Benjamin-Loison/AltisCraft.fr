package fr.altiscraft.benjaminloison.proxy;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import fr.altiscraft.benjaminloison.entity.EEPNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomizableNPC extends RenderBiped
{
    public ResourceLocation locationSkin;
    public ThreadDownloadImageData downloadImageSkin = null;
    public String textureChanged;

    public RenderCustomizableNPC(ModelBiped model, float shadow)
    {
        super(model, shadow);
    }

    protected ResourceLocation getEntityTexture(EntityLiving living)
    {
        EEPNPC props = EEPNPC.get(living);
        String texture = living.getDataWatcher().getWatchableObjectString(20);
        if(props.texture != texture)
            props.downloadImageHat = null;
        if(props.downloadImageHat == null)
            props.downloadImageHat = props.getDownloadImageHat(props.getLocationHat(texture), texture);
        props.texture = texture;
        return props.getLocationHat(texture);
    }
}
