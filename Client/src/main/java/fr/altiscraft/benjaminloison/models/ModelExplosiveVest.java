package fr.altiscraft.benjaminloison.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelExplosiveVest extends ModelBiped
{
    ModelRenderer one, two, three, four, five, six, seven, eight, nine;

    public ModelExplosiveVest()
    {
        textureWidth = 512;
        textureHeight = 512;
        one = new ModelRenderer(this, 448, 448);
        one.addBox(2, 1, -3, 2, 1, 1);
        one.setRotationPoint(0, 0, 0);
        one.setTextureSize(512, 512);
        setRotation(one, 0, 0, 0);
        two = new ModelRenderer(this, 448, 448);
        two.addBox(4, 1, -2, 1, 11, 4);
        two.setRotationPoint(0, 0, 0);
        two.setTextureSize(512, 512);
        setRotation(two, 0, 0, 0);
        three = new ModelRenderer(this, 448, 448);
        three.addBox(-4, 1, -3, 2, 1, 1);
        three.setRotationPoint(0, 0, 0);
        three.setTextureSize(512, 512);
        setRotation(three, 0, 0, 0);
        four = new ModelRenderer(this, 448, 448);
        four.addBox(-4, 1, 2, 8, 11, 1);
        four.setRotationPoint(0, 0, 0);
        four.setTextureSize(512, 512);
        setRotation(four, 0, 0, 0);
        five = new ModelRenderer(this, 448, 448);
        five.addBox(-5, 1, -2, 1, 11, 4);
        five.setRotationPoint(0, 0, 0);
        five.setTextureSize(512, 512);
        setRotation(five, 0, 0, 0);
        six = new ModelRenderer(this, 448, 448);
        six.addBox(-4, 2, -3, 8, 10, 1);
        six.setRotationPoint(0, 0, 0);
        six.setTextureSize(512, 512);
        setRotation(six, 0, 0, 0);
        seven = new ModelRenderer(this, 448, 480);
        seven.addBox(-3, 8F, -4, 6, 3, 1);
        seven.setRotationPoint(0, 0, 0);
        seven.setTextureSize(512, 512);
        setRotation(seven, 0, 0, 0);
        eight = new ModelRenderer(this, 448, 480);
        eight.addBox(-3, 3, -4, 2, 4, 1);
        eight.setRotationPoint(0, 0, 0);
        eight.setTextureSize(512, 512);
        setRotation(eight, 0, 0, 0);
        nine = new ModelRenderer(this, 448, 480);
        nine.addBox(1, 3, -4, 2, 4, 1);
        nine.setRotationPoint(0, 0, 0);
        nine.setTextureSize(512, 512);
        setRotation(nine, 0, 0, 0);
        bipedBody.addChild(one);
        bipedBody.addChild(two);
        bipedBody.addChild(three);
        bipedBody.addChild(four);
        bipedBody.addChild(five);
        bipedBody.addChild(six);
        bipedBody.addChild(seven);
        bipedBody.addChild(eight);
        bipedBody.addChild(nine);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        one.render(f5);
        two.render(f5);
        three.render(f5);
        four.render(f5);
        five.render(f5);
        six.render(f5);
        seven.render(f5);
        eight.render(f5);
        nine.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}