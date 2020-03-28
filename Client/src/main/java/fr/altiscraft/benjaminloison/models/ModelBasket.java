package fr.altiscraft.benjaminloison.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBasket extends ModelBase
{
    ModelRenderer one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fiveteen;

    public ModelBasket()
    {
        textureWidth = 128;
        textureHeight = 64;
        one = new ModelRenderer(this, 90, 0);
        one.addBox(0, 0, 0, 1, 1, 5);
        one.setRotationPoint(3, -11, 0);
        one.setTextureSize(128, 64);
        setRotation(one, 0, 0, 0);
        two = new ModelRenderer(this, 0, 0);
        two.addBox(0, 0, 0, 2, 48, 2);
        two.setRotationPoint(-1, -24, 6);
        two.setTextureSize(128, 64);
        setRotation(two, 0, 0, 0);
        three = new ModelRenderer(this, 25, 0);
        three.addBox(0, 0, 0, 24, 16, 1);
        three.setRotationPoint(-12, -24, 5);
        three.setTextureSize(128, 64);
        setRotation(three, 0, 0, 0);
        four = new ModelRenderer(this, 90, 0);
        four.addBox(0, 0, 0, 1, 1, 1);
        four.setRotationPoint(4, -11, -1);
        four.setTextureSize(128, 64);
        setRotation(four, 0, 0, 0);
        five = new ModelRenderer(this, 87, 0);
        five.addBox(0, 0, 0, 1, 1, 5);
        five.setRotationPoint(-4, -11, 0);
        five.setTextureSize(128, 64);
        setRotation(five, 0, 0, 0);
        six = new ModelRenderer(this, 87, 0);
        six.addBox(0, 0, 0, 1, 1, 8);
        six.setRotationPoint(-7, -11, -10);
        six.setTextureSize(128, 64);
        setRotation(six, 0, 0, 0);
        seven = new ModelRenderer(this, 90, 0);
        seven.addBox(0, 0, 0, 1, 1, 1);
        seven.setRotationPoint(-5, -11, -1);
        seven.setTextureSize(128, 64);
        setRotation(seven, 0, 0, 0);
        eight = new ModelRenderer(this, 90, 0);
        eight.addBox(0, 0, 0, 1, 1, 1);
        eight.setRotationPoint(-5, -11, -12);
        eight.setTextureSize(128, 64);
        setRotation(eight, 0, 0, 0);
        nine = new ModelRenderer(this, 90, 0);
        nine.addBox(0, 0, 0, 1, 1, 1);
        nine.setRotationPoint(5, -11, -2);
        nine.setTextureSize(128, 64);
        setRotation(nine, 0, 0, 0);
        ten = new ModelRenderer(this, 90, 0);
        ten.addBox(0, 0, 0, 1, 1, 8);
        ten.setRotationPoint(6, -11, -10);
        ten.setTextureSize(128, 64);
        setRotation(ten, 0, 0, 0);
        eleven = new ModelRenderer(this, 90, 0);
        eleven.addBox(0, 0, 0, 1, 1, 1);
        eleven.setRotationPoint(-6, -11, -2);
        eleven.setTextureSize(128, 64);
        setRotation(eleven, 0, 0, 0);
        twelve = new ModelRenderer(this, 90, 0);
        twelve.addBox(0, 0, 0, 8, 1, 1);
        twelve.setRotationPoint(-4, -11, -13);
        twelve.setTextureSize(128, 64);
        setRotation(twelve, 0, 0, 0);
        thirteen = new ModelRenderer(this, 90, 0);
        thirteen.addBox(0, 0, 0, 1, 1, 1);
        thirteen.setRotationPoint(-6, -11, -11);
        thirteen.setTextureSize(128, 64);
        setRotation(thirteen, 0, 0, 0);
        fourteen = new ModelRenderer(this, 90, 0);
        fourteen.addBox(0, 0, 0, 1, 1, 1);
        fourteen.setRotationPoint(5, -11, -11);
        fourteen.setTextureSize(128, 64);
        setRotation(fourteen, 0, 0, 0);
        fiveteen = new ModelRenderer(this, 90, 0);
        fiveteen.addBox(0, 0, 0, 1, 1, 1);
        fiveteen.setRotationPoint(4, -11, -12);
        fiveteen.setTextureSize(128, 64);
        setRotation(fiveteen, 0, 0, 0);
    }

    public void renderAll()
    {
        one.render(0.0625F);
        two.render(0.0625F);
        three.render(0.0625F);
        four.render(0.0625F);
        five.render(0.0625F);
        six.render(0.0625F);
        seven.render(0.0625F);
        eight.render(0.0625F);
        nine.render(0.0625F);
        ten.render(0.0625F);
        eleven.render(0.0625F);
        twelve.render(0.0625F);
        thirteen.render(0.0625F);
        fourteen.render(0.0625F);
        fiveteen.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
