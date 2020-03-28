package fr.altiscraft.benjaminloison.api;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import fr.altiscraft.benjaminloison.common.AltisCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class FileAPI
{
    public final static String path = new File("").getAbsolutePath() + File.separatorChar, acfr = path + "mods" + File.separatorChar + AltisCraft.NAME + File.separatorChar;
    public final static File controller = new File(acfr + "Controller.txt"), accessories = new File(acfr + "Accessories.txt"), namesList = new File(acfr + Minecraft.getMinecraft().getSession().getUsername() + ".dat");

    public static String number(long number)
    {
        if(!Boolean.parseBoolean(I18n.format("money.with.spaces")))
            return "" + number;
        String nbr = "" + number;
        if(nbr.length() == 4)
            nbr = nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 5)
            nbr = nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 6)
            nbr = nbr.charAt(nbr.length() - 6) + "" + nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 7)
            nbr = nbr.charAt(nbr.length() - 7) + " " + nbr.charAt(nbr.length() - 6) + "" + nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 8)
            nbr = nbr.charAt(nbr.length() - 8) + "" + nbr.charAt(nbr.length() - 7) + " " + nbr.charAt(nbr.length() - 6) + "" + nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 9)
            nbr = nbr.charAt(nbr.length() - 9) + "" + nbr.charAt(nbr.length() - 8) + "" + nbr.charAt(nbr.length() - 7) + " " + nbr.charAt(nbr.length() - 6) + "" + nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 10)
            nbr = nbr.charAt(nbr.length() - 10) + " " + nbr.charAt(nbr.length() - 9) + "" + nbr.charAt(nbr.length() - 8) + "" + nbr.charAt(nbr.length() - 7) + " " + nbr.charAt(nbr.length() - 6) + "" + nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        else if(nbr.length() == 11)
            nbr = nbr.charAt(nbr.length() - 11) + "" + nbr.charAt(nbr.length() - 10) + " " + nbr.charAt(nbr.length() - 9) + "" + nbr.charAt(nbr.length() - 8) + "" + nbr.charAt(nbr.length() - 7) + " " + nbr.charAt(nbr.length() - 6) + "" + nbr.charAt(nbr.length() - 5) + "" + nbr.charAt(nbr.length() - 4) + " " + nbr.charAt(nbr.length() - 3) + "" + nbr.charAt(nbr.length() - 2) + "" + nbr.charAt(nbr.length() - 1);
        return nbr;
    }

    public static String read(File file)
    {
        try
        {
            Scanner scanner = new Scanner(file);
            String lign = scanner.nextLine();
            scanner.close();
            return lign;
        }
        catch(Exception e)
        {}
        return null;
    }

    public static String[] arrayListToString(ArrayList<String> array)
    {
        String[] strings = new String[array.size()];
        for(int i = 0; i < strings.length; i++)
            strings[i] = array.get(i);
        return strings;
    }

    public static long configNumber(String string)
    {
        String config = I18n.format(string);
        if(StringUtils.isNumeric(config))
            return Long.parseLong(config);
        AltisCraft.print("ERROR ! " + string + " lang " + config + " is not numeric !");
        return 0;
    }

    public static int configNumberInt(String string)
    {
        String config = I18n.format(string);
        if(StringUtils.isNumeric(config))
            return Integer.parseInt(config);
        AltisCraft.print("ERROR ! " + string + " lang " + config + " is not numeric !");
        return 0;
    }

    public static boolean configBoolean(String string)
    {
        return Boolean.getBoolean(I18n.format(string));
    }

    public static void write(File file, String lign)
    {
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(lign);
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void remove(File file, String toRemove)
    {
        try
        {
            String toWrite = read(file).replace(toRemove, "");
            file.delete();
            FileWriter fw = new FileWriter(file);
            fw.write(toWrite);
            fw.close();
        }
        catch(Exception e)
        {}
    }
}