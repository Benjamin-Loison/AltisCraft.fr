package fr.benjaminloison.altiscraft.api;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.benjaminloison.altiscraft.main.AltisCraft;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class FileAPI
{
    public final static String path = new File("").getAbsolutePath() + File.separatorChar, acfr = path + "mods" + File.separatorChar + "AltisCraft.fr" + File.separatorChar, carShop = acfr + "Car Shop" + File.separatorChar, spawn = acfr + "Spawn" + File.separatorChar, gang = acfr + "Gang" + File.separatorChar, plac = path + "plugins" + File.separatorChar + "AltisCraft.fr" + File.separatorChar;
    public final static File languageFile = new File(FileAPI.plac + "Language.txt");

    public static String number(long number)
    {
        if(!languageFile.exists())
            write("money.with.spaces=true\nautorestart=false", languageFile);
        else if(translate("money.with.spaces").equalsIgnoreCase("false"))
            return translate("msg.money") + number + translate("currency");
        String n = number + "";
        int l = n.length();
        if(n.length() == 4)
            n = n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 5)
            n = n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 6)
            n = n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 7)
            n = n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 8)
            n = n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 9)
            n = n.charAt(n.length() - 9) + "" + n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 10)
            n = n.charAt(n.length() - 10) + " " + n.charAt(n.length() - 9) + "" + n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 11)
            n = n.charAt(n.length() - 11) + "" + n.charAt(n.length() - 10) + " " + n.charAt(n.length() - 9) + "" + n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        return translate("msg.money") + n + translate("currency");
    }

    public static String numberNormal(long number)
    {
        String n = number + "";
        int l = n.length();
        if(n.length() == 4)
            n = n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 5)
            n = n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 6)
            n = n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 7)
            n = n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 8)
            n = n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 9)
            n = n.charAt(n.length() - 9) + "" + n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 10)
            n = n.charAt(n.length() - 10) + " " + n.charAt(n.length() - 9) + "" + n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        else if(n.length() == 11)
            n = n.charAt(n.length() - 11) + "" + n.charAt(n.length() - 10) + " " + n.charAt(n.length() - 9) + "" + n.charAt(n.length() - 8) + "" + n.charAt(n.length() - 7) + " " + n.charAt(n.length() - 6) + "" + n.charAt(n.length() - 5) + "" + n.charAt(n.length() - 4) + " " + n.charAt(n.length() - 3) + "" + n.charAt(n.length() - 2) + "" + n.charAt(n.length() - 1);
        return n;
    }

    public static Date stringToDate(String time)
    {
        try
        {
            String parts[] = time.split(" ");
            if(parts[parts.length - 1].charAt(1) == 'M')
            {
                String[] buffer = parts;
                parts = new String[4];
                parts[0] = buffer[1].replace(",", "");
                parts[1] = translateMonth(buffer[0]);
                parts[2] = buffer[2];
                if(parts[parts.length - 1].equals("PM"))
                {
                    String[] times = buffer[3].split(":");
                    parts[3] = Integer.parseInt(times[0]) + 12 + ":" + times[1] + ":" + times[2];
                }
                else
                    parts[3] = buffer[3];
            }
            if(Integer.parseInt(parts[0]) > 9)
                return new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").parse(parts[2] + "-" + monthToNumber(parts[1]) + "-" + parts[0] + "-" + parts[3]);
            else
                return new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").parse(parts[2] + "-" + monthToNumber(parts[1]) + "-" + 0 + parts[0] + "-" + parts[3]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String translateMonth(String month)
    {
        if(month.startsWith("Jan"))
            return "janvier";
        else if(month.startsWith("Feb"))
            return "février";
        else if(month.startsWith("Mar"))
            return "mars";
        else if(month.startsWith("Apr"))
            return "avril";
        else if(month.startsWith("May"))
            return "mai";
        else if(month.startsWith("Jun"))
            return "juin";
        else if(month.startsWith("Jul"))
            return "juillet";
        else if(month.startsWith("Aug"))
            return "août";
        else if(month.startsWith("Sep"))
            return "septembre";
        else if(month.startsWith("Oct"))
            return "octobre";
        else if(month.startsWith("Nov"))
            return "novembre";
        else if(month.startsWith("Dec"))
            return "décembre";
        return "";
    }

    public static String monthToNumber(String month)
    {
        if(month.startsWith("jan"))
            return "01";
        else if(month.startsWith("fév"))
            return "02";
        else if(month.startsWith("mar"))
            return "03";
        else if(month.startsWith("avr"))
            return "04";
        else if(month.startsWith("mai"))
            return "05";
        else if(month.equals("juin"))
            return "06";
        else if(month.equals("juil"))
            return "07";
        else if(month.startsWith("aoû"))
            return "08";
        else if(month.startsWith("sep"))
            return "09";
        else if(month.startsWith("oct"))
            return "10";
        else if(month.startsWith("nov"))
            return "11";
        else if(month.startsWith("déc"))
            return "12";
        return "00";
    }

    public static String afterPoint(String path)
    {
        int charac = 0;
        for(int i = 0; i < path.length(); i++)
            if(path.charAt(i) == '.')
                charac = i;
        return path.substring(charac, path.length());
    }

    public static void write(String[] ligns, File file)
    {
        try
        {
            FileWriter fw = new FileWriter(file, true);
            for(int i = 0; i < ligns.length; i++)
                if(!(i == ligns.length - 1))
                    fw.write(ligns[i] + "\n");
                else
                    fw.write(ligns[i]);
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void write(String lign, File file)
    {
        try
        {
            FileWriter fw = new FileWriter(file, true);
            fw.write(lign);
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String[] readAll(File file)
    {
        try
        {
            int size = 0;
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                scan.nextLine();
                size++;
            }
            String[] ligns = new String[size];
            scan.close();
            Scanner scanner = new Scanner(file);
            for(int i = 0; i < ligns.length; i++)
                ligns[i] = scanner.nextLine();
            scanner.close();
            return ligns;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Location location(World world, File file)
    {
        String[] co = read(file).split(",");
        return new Location(world, Integer.parseInt(co[0]), Integer.parseInt(co[1]), Integer.parseInt(co[2]));
    }

    public static void removeLign(String lign, File file)
    {
        try
        {
            int ligns = 0;
            File tempFile = new File(file.getAbsolutePath().replace(".txt", ".temp"));
            FileWriter fw = new FileWriter(tempFile);
            Scanner scan = new Scanner(file), scanner = new Scanner(file);
            while(scan.hasNextLine())
            {
                scanner.nextLine();
                String read = scan.nextLine();
                if(!read.equalsIgnoreCase(lign))
                {
                    if(scan.hasNextLine() && scanner.hasNextLine())
                        fw.write(read + "\n");
                    else
                        fw.write(read);
                    ligns++;
                }
            }
            fw.close();
            scan.close();
            scanner.close();
            file.delete();
            tempFile.renameTo(file);
            if(ligns == 0)
            {
                scan.close();
                file.delete();
            }
            scan.close();
            tempFile.delete();
        }
        catch(Exception e)
        {}
    }

    public static void addLign(String lign, File file)
    {
        try
        {
            if(file.exists())
            {
                Scanner scan = new Scanner(file);
                FileWriter fw = new FileWriter(file, true);
                if(scan.hasNextLine())
                {
                    scan.close();
                    fw.write("\n" + lign);
                }
                else
                {
                    scan.close();
                    fw.write(lign);
                }
                fw.close();
            }
            else
            {
                FileWriter fw = new FileWriter(file, true);
                fw.write(lign);
                fw.close();
            }
        }
        catch(Exception e)
        {}
    }

    public static String read(File file)
    {
        try
        {
            Scanner scan = new Scanner(file);
            String buffer = scan.nextLine();
            scan.close();
            return buffer;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    public static void ifNotExisteWrite(File file, String lign)
    {
        if(!file.exists())
            try
            {
                FileWriter fw = new FileWriter(file);
                fw.write(lign);
                fw.close();
            }
            catch(Exception e)
            {}
    }

    public static String readOrWrite(File file, int number, String lign)
    {
        try
        {
            Scanner scan = new Scanner(file);
            for(int i = 1; i <= number; i++)
                if(scan.hasNextLine())
                    scan.nextLine();
            String buffer = "";
            if(scan.hasNextLine())
                buffer = scan.nextLine();
            scan.close();
            return buffer;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    public static String translate(String base)
    {
        try
        {
            if(!languageFile.exists())
                write("money.with.spaces=true", languageFile);
            Scanner scan = new Scanner(languageFile, "UTF-8");
            while(scan.hasNextLine())
            {
                String lign = scan.nextLine();
                if(lign.startsWith(base + "="))
                {
                    scan.close();
                    return lign.replace(base + "=", "");
                }
            }
            scan.close();
        }
        catch(Exception e)
        {
            System.out.println("Error found for: " + base + " in: " + languageFile);
            e.printStackTrace();
        }
        System.out.println("No translation found for: " + base + " in: " + languageFile);
        return base;
    }

    public static Long config(String base)
    {
        String config = translate(base);
        if(StringUtils.isNumeric(config))
            return Long.parseLong(config);
        else
        {
            System.out.println(config + " is not numeric !");
            return 0L;
        }
    }

    public static String findGang(String player)
    {
        MCNBTDataAPI data = new MCNBTDataAPI(new File(FileAPI.path + Bukkit.getWorlds().get(0).getName() + File.separatorChar + "playerdata" + File.separatorChar + Bukkit.getPlayer(player).getUniqueId() + ".dat"));
        if(data != null)
            if(data.hasCompoundTag(AltisCraft.NAME))
                return data.valueKeyInCompoundTag(data.getCompoundTag(AltisCraft.NAME), "Gang Inviting");
        return "";
    }

    public static boolean isOfficial(String gangName)
    {
        try
        {
            Scanner scan = new Scanner(new File(gang + "Official.yml"));
            while(scan.hasNextLine())
                if(scan.nextLine().equals(gangName))
                {
                    scan.close();
                    return true;
                }
            scan.close();
        }
        catch(Exception e)
        {}
        return false;
    }
}