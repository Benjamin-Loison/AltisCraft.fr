package fr.altiscraft.benjaminloison.api;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class NameAPI
{
    public static void check(String[] names, EntityPlayer player)
    {
        ArrayList<String> namesNotFound = new ArrayList<String>();
        for(int i = 0; i < names.length; i++)
            if(!isInList(names[i], player))
                namesNotFound.add(names[i]);
        add(FileAPI.arrayListToString(namesNotFound), player);
    }

    public static void add(String[] names, EntityPlayer player)
    {
        for(int i = 0; i < names.length; i++)
            FileAPI.write(FileAPI.namesList, " " + names[i]);
    }

    public static void add(UUID name, EntityPlayer player)
    {
        FileAPI.write(FileAPI.namesList, " " + name.toString());
    }

    public static boolean remove(UUID name, EntityPlayer player)
    {
        FileAPI.remove(FileAPI.namesList, " " + name.toString());
        return false;
    }

    public static boolean isInList(String name, EntityPlayer player)
    {
        String[] namesWritten = namesOfNamesList(player);
        for(int i = 0; i < namesWritten.length; i++)
            if(name.equals(namesWritten[i]))
                return true;
        return false;
    }

    private static String[] namesOfNamesList(EntityPlayer player)
    {
        String names = FileAPI.read(FileAPI.namesList), buffer[] = {""};
        if(names == null || names.equals(""))
            return buffer;
        return names.split(" ");
    }
}