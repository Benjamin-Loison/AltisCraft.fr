package fr.benjaminloison.altiscraft.api;

public class PlayerAPI
{
    public static String main(String name)
    {
        return firstUpper(name.split("_")[0]) + "_" + firstUpper(name.split("_")[1]);
    }

    public static String firstUpper(String str)
    {
        String string = str;
        str = str.toUpperCase().charAt(0) + "";
        for(int i = 1; i < string.length(); i++)
            str = str + string.charAt(i);
        return str;
    }
}