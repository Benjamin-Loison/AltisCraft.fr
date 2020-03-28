package fr.altiscraft.benjaminloison.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelShutter extends ModelBase
{
    ModelRenderer one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fiveteen, sixteen, seventeen, eighteen, nineteen, twenty;

    public ModelShutter()
    {
        textureWidth = 64;
        textureHeight = 128;
        one = new ModelRenderer(this, 0, 0);
        one.addBox(0, 0, 0, 14, 1, 1);
        one.setRotationPoint(-7, -6, 6);
        one.setTextureSize(64, 128);
        setRotation(one, 0, 0, 0);
        two = new ModelRenderer(this, 19, 40);
        two.addBox(0, 0, 0, 16, 32, 1);
        two.setRotationPoint(-8, -8, 7);
        two.setTextureSize(64, 128);
        setRotation(two, 0, 0, 0);
        three = new ModelRenderer(this, 0, 0);
        three.addBox(0, 0, 0, 14, 1, 1);
        three.setRotationPoint(-7, 7.5F, 6);
        three.setTextureSize(64, 128);
        setRotation(three, 0, 0, 0);
        four = new ModelRenderer(this, 39, 0);
        four.addBox(0, 0, 0, 1, 32, 1);
        four.setRotationPoint(7, -8, 6);
        four.setTextureSize(64, 128);
        setRotation(four, 0, 0, 0);
        five = new ModelRenderer(this, 0, 0);
        five.addBox(0, 0, 0, 14, 1, 1);
        five.setRotationPoint(-7, -4, 6);
        five.setTextureSize(64, 128);
        setRotation(five, 0, 0, 0);
        six = new ModelRenderer(this, 0, 0);
        six.addBox(0, 0, 0, 14, 1, 1);
        six.setRotationPoint(-7, -2, 6);
        six.setTextureSize(64, 128);
        setRotation(six, 0, 0, 0);
        seven = new ModelRenderer(this, 0, 0);
        seven.addBox(0, 0, 0, 14, 1, 1);
        seven.setRotationPoint(-7, 0, 6);
        seven.setTextureSize(64, 128);
        setRotation(seven, 0, 0, 0);
        eight = new ModelRenderer(this, 0, 0);
        eight.addBox(0, 0, 0, 14, 1, 1);
        eight.setRotationPoint(-7, 2, 6);
        eight.setTextureSize(64, 128);
        setRotation(eight, 0, 0, 0);
        nine = new ModelRenderer(this, 0, 0);
        nine.addBox(0, 0, 0, 14, 1, 1);
        nine.setRotationPoint(-7, 4, 6);
        nine.setTextureSize(64, 128);
        setRotation(nine, 0, 0, 0);
        ten = new ModelRenderer(this, 0, 0);
        ten.addBox(0, 0, 0, 14, 1, 1);
        ten.setRotationPoint(-7, 9, 6);
        ten.setTextureSize(64, 128);
        setRotation(ten, 0, 0, 0);
        eleven = new ModelRenderer(this, 0, 0);
        eleven.addBox(0, 0, 0, 14, 1, 1);
        eleven.setRotationPoint(-7, 11, 6);
        eleven.setTextureSize(64, 128);
        setRotation(eleven, 0, 0, 0);
        twelve = new ModelRenderer(this, 0, 0);
        twelve.addBox(0, 0, 0, 14, 1, 1);
        twelve.setRotationPoint(-7, 13, 6);
        twelve.setTextureSize(64, 128);
        setRotation(twelve, 0, 0, 0);
        thirteen = new ModelRenderer(this, 0, 0);
        thirteen.addBox(0, 0, 0, 14, 1, 1);
        thirteen.setRotationPoint(-7, 15, 6);
        thirteen.setTextureSize(64, 128);
        setRotation(thirteen, 0, 0, 0);
        fourteen = new ModelRenderer(this, 0, 0);
        fourteen.addBox(0, 0, 0, 14, 1, 1);
        fourteen.setRotationPoint(-7, 17, 6);
        fourteen.setTextureSize(64, 128);
        setRotation(fourteen, 0, 0, 0);
        fiveteen = new ModelRenderer(this, 0, 39);
        fiveteen.addBox(0, 0, 0, 14, 1, 1);
        fiveteen.setRotationPoint(-7, 23, 6);
        fiveteen.setTextureSize(64, 128);
        setRotation(fiveteen, 0, 0, 0);
        sixteen = new ModelRenderer(this, 0, 0);
        sixteen.addBox(0, 0, 0, 14, 1, 1);
        sixteen.setRotationPoint(-7, 21, 6);
        sixteen.setTextureSize(64, 128);
        setRotation(sixteen, 0, 0, 0);
        seventeen = new ModelRenderer(this, 0, 0);
        seventeen.addBox(0, 0, 0, 14, 1, 1);
        seventeen.setRotationPoint(-7, 19, 6);
        seventeen.setTextureSize(64, 128);
        setRotation(seventeen, 0, 0, 0);
        eighteen = new ModelRenderer(this, 0, 0);
        eighteen.addBox(0, 0, 0, 14, 1, 1);
        eighteen.setRotationPoint(-7, 6, 6);
        eighteen.setTextureSize(64, 128);
        setRotation(eighteen, 0, 0, 0);
        nineteen = new ModelRenderer(this, 2, 38);
        nineteen.addBox(0, 0, 0, 14, 1, 1);
        nineteen.setRotationPoint(-7, -8, 6);
        nineteen.setTextureSize(64, 128);
        setRotation(nineteen, 0, 0, 0);
        twenty = new ModelRenderer(this, 40, 0);
        twenty.addBox(0, 0, 0, 1, 32, 1);
        twenty.setRotationPoint(-8, -8, 6);
        twenty.setTextureSize(64, 128);
        setRotation(twenty, 0, 0, 0);
    }

    public void renderAll()
    {
        two.render(0.0625F);
        one.render(0.0625F);
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
        sixteen.render(0.0625F);
        seventeen.render(0.0625F);
        eighteen.render(0.0625F);
        nineteen.render(0.0625F);
        twenty.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}