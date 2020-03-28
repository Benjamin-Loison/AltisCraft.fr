package fr.altiscraft.benjaminloison.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelFlag extends ModelBase
{
    ModelRenderer one, two, three, four;

    public ModelFlag()
    {
        textureWidth = 128;
        textureHeight = 256;
        one = new ModelRenderer(this, 49, 101);
        one.addBox(0, 0, 0, 16, 32, 2);
        one.setRotationPoint(-34, -100, 1);
        one.setTextureSize(128, 256);
        setRotation(one, 0, 0, 0);
        two = new ModelRenderer(this, 0, 0);
        two.addBox(0, 0, 0, 4, 128, 4);
        two.setRotationPoint(-2, -104, -2);
        two.setTextureSize(128, 256);
        setRotation(two, 0, 0, 0);
        three = new ModelRenderer(this, 45, 0);
        three.addBox(0, 0, 0, 16, 32, 2);
        three.setRotationPoint(-50, -100, -1);
        three.setTextureSize(128, 256);
        setRotation(three, 0, 0, 0);
        four = new ModelRenderer(this, 50, 188);
        four.addBox(0, 0, 0, 16, 32, 2);
        four.setRotationPoint(-18, -100, -1);
        four.setTextureSize(128, 256);
        setRotation(four, 0, 0, 0);
    }

    public void renderAll()
    {
        one.render(0.0625F);
        two.render(0.0625F);
        three.render(0.0625F);
        four.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}