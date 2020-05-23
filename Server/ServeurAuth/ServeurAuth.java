import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ServeurAuth
{
    static boolean restarting, count = true;
    final static int port = 59723;
    static byte buffer[] = new byte[Integer.MAX_VALUE / 2];
    static String path = new File("").getAbsolutePath() + File.separatorChar, minecraftServer = "[MCServer] ", fatalAlert = "[FATAL ALERT] ", directory = File.separatorChar + "home" + File.separatorChar + "altiscraft" + File.separatorChar + "AltisCraft.fr" + File.separatorChar,
            ts = File.separatorChar + "home" + File.separatorChar + "AltisCraft.fr" + File.separatorChar + "TS" + File.separatorChar + "TS.php";
    static File accessories = new File(path + "Accessories.txt"), log = new File(path + "Log" + File.separatorChar + "Log.log"), logZB = new File(path + "Log" + File.separatorChar + "LogZB.log"), logZBA = new File(path + "Log" + File.separatorChar + "LogZBA.log"), fichier, data = new File(path + "Data"), ipsNotSafeFile = new File(path + "Banned-ips.txt");
    static DatagramSocket socket;

    private static boolean needZero(String f)
    {
        try
        {
            Scanner scan = new Scanner(new File("whitelist.txt"));
            while(scan.hasNextLine())
                if(scan.nextLine().contains(f.replace(".txt", "")))
                {
                    scan.close();
                    return false;
                }
            // !f.contains("54.38.241.7") && !f.contains("90.127.196.172") &&
            // !f.contains("192.168.1.") && !f.contains("109.30.118.83") &&
            // !f.contains("82.124.163.161") && !f.contains("100.112.35.29")
            // &&
            // !f.contains("90.91.55.178") &&
            scan.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return f.endsWith(".txt");
    }

    public static <ProcessBuild> void main(String args[])
    {
        String startMsg = "Lancement du serveur d'Authentification sur le port " + port + " !";
        read(startMsg);
        writeZBA(startMsg);

        log.mkdirs();
        data.mkdirs();

        while(true)
        {
            try
            {
                socket = new DatagramSocket(port);
                int i, IP = 0, warning = 0;
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length), send;
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                String ip = address.toString().replace("/", "");
                String realPassword = "", filesList[], type = "", info0 = "", info1 = "", info2 = "", packetData = new String(packet.getData(), 0, packet.getLength()), parts[] = packetData.split(" ");
                if(parts.length >= 1)
                {
                    type = parts[0];
                    if(parts.length >= 2)
                    {
                        info0 = parts[1];
                        if(parts.length >= 3)
                        {
                            info1 = parts[2];
                            if(parts.length >= 4)
                                info2 = parts[3];
                        }
                    }
                }
                read(ip);
                if(isSafeIP(ip))
                {
                    read("Données arrivées non traitées : " + packetData);
                    if(type.equals("R"))
                    {
                        read("Enregistrement...");
                        read("Pseudo = " + info0);
                        File registrationFile = new File(path + File.separatorChar + "Data" + File.separatorChar + info0 + "!" + packet.getAddress().toString().replaceAll("/", "") + ".txt");
                        if(registrationFile.exists())
                        {
                            read("Déjà enregistré.");
                            socket.send(new DatagramPacket("0".getBytes(), "0".length(), packet.getAddress(), packet.getPort()));
                        }
                        else
                        {
                            filesList = data.list();
                            for(i = 0; i < filesList.length; i++)
                                if(filesList[i].startsWith(info0 + "!"))
                                {
                                    warning = 1;
                                    break;
                                }
                            if(warning != 1)
                            {
                                read("Non enregistré...");
                                read("Mot de passe = " + info1);
                                FileWriter fw = new FileWriter(new File(path + File.separatorChar + "Data" + File.separatorChar + info0 + "!" + packet.getAddress().toString().replaceAll("/", "") + ".txt"));
                                fw.write(info1);
                                fw.close();
                                read("Inscription réussite !");
                                socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                                warning = 0;
                            }
                            else
                            {
                                read("Déjà inscrit !");
                                socket.send(new DatagramPacket("0".getBytes(), "0".length(), packet.getAddress(), packet.getPort()));
                            }
                        }
                    }
                    else if(type.equals("C"))
                    {
                        read("Connexion automatique...");
                        if(new File(path + File.separatorChar + "Data" + File.separatorChar + info0 + "!" + packet.getAddress().toString().replaceAll("/", "") + ".txt").exists())
                        {
                            socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                            read("Connexion réussie ! (Fichier éxistant)");
                        }
                        else
                        {
                            filesList = data.list();
                            for(i = 0; i < filesList.length; i++)
                                if(filesList[i].startsWith(info0 + "!"))
                                {
                                    IP = 1;
                                    break;
                                }
                            if(IP == 0)
                            {
                                socket.send(new DatagramPacket("2".getBytes(), "2".length(), packet.getAddress(), packet.getPort()));
                                read("Connexion échouée ! (Fichier inéxistant)");
                            }
                            else
                            {
                                socket.send(new DatagramPacket("3".getBytes(), "3".length(), packet.getAddress(), packet.getPort()));
                                read("Connexion échouée ! (Fichier éxistant mais mauvaise IP)");
                            }
                        }
                    }
                    /*
                     * else if(type.equals("A")) { read("Changement de mot de passe...");
                     * read("Pseudo = " + info0); Scanner scan = new Scanner(new File(path +
                     * File.separatorChar + "Data" + File.separatorChar + info0 + "!" +
                     * packet.getAddress().toString().replaceAll("/", "") + ".txt")); String
                     * oldPassword = "NoAttributionException"; if(scan.hasNextLine()) oldPassword =
                     * scan.nextLine(); read("Ancien mot de passe = " + oldPassword);
                     * read("Nouveau mot de passe = " + info1); File actualFile = new File(path +
                     * File.separatorChar + "Data" + File.separatorChar + info0 + "!" +
                     * packet.getAddress().toString().replaceAll("/", "") + ".txt"), temporaryFile =
                     * new File(path + File.separatorChar + "Data" + File.separatorChar + info0 +
                     * "!" + packet.getAddress().toString().replaceAll("/", "") + ".temp");
                     * scan.close(); actualFile.renameTo(temporaryFile); FileWriter fw = new
                     * FileWriter(actualFile); fw.write(info1); fw.close(); temporaryFile.delete();
                     * socket.send(new DatagramPacket("-1".getBytes(), "-1".length(),
                     * packet.getAddress(), packet.getPort()));
                     * read("Changement de mot de passe réussi !"); }
                     */
                    else if(type.equals("N"))
                    {
                        read("Test disponibilité serveur...");
                        Scanner scan = new Scanner(directory + "eta.txt");
                        if(Boolean.parseBoolean(scan.nextLine()))
                        {
                            socket.send(new DatagramPacket("0".getBytes(), "0".length(), packet.getAddress(), packet.getPort()));
                            read("Envoi résultat: serveur indisponible !");
                        }
                        else
                        {
                            socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                            read("Envoi résultat: serveur disponible !");
                        }
                        scan.close();
                    }
                    else if(type.equals("L"))
                    {
                        read("Connexion...");
                        read("Pseudo = " + info0);
                        File connectFile = new File(path + File.separatorChar + "Data" + File.separatorChar + info0 + "!" + packet.getAddress().toString().replaceAll("/", "") + ".txt");
                        read("Mot de passe = " + info1);
                        if(connectFile.exists())
                        {
                            Scanner scan = new Scanner(connectFile);
                            if(scan.hasNextLine())
                                realPassword = scan.nextLine();
                            read("Vrai mot de passe : " + realPassword);
                            if(realPassword.equals(info1))
                            {
                                socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                                read("Connexion réussie !");
                            }
                            else
                            {
                                socket.send(new DatagramPacket("0".getBytes(), "0".length(), packet.getAddress(), packet.getPort()));
                                read("Connexion échouée !");
                            }
                            scan.close();
                        }
                        else
                        {
                            filesList = data.list();
                            for(i = 0; i < filesList.length; i++)
                                if(IP == 0)
                                    if(filesList[i].startsWith(info0 + "!"))
                                    {
                                        Scanner scan = new Scanner(new File(path + File.separatorChar + "Data" + File.separatorChar + filesList[i]));
                                        fichier = new File(path + File.separatorChar + "Data" + File.separatorChar + filesList[i]);
                                        if(scan.hasNextLine())
                                        {
                                            String realmdp = scan.nextLine();
                                            if(realmdp.equals(info1))
                                            {
                                                socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                                                FileWriter fw = new FileWriter(new File(path + File.separatorChar + "Data" + File.separatorChar + info0 + "!" + packet.getAddress().toString().replaceAll("/", "") + ".txt"));
                                                fw.write(info1);
                                                fw.close();
                                                scan.close();
                                                fichier.delete();
                                                read("Connexion réussie et IP changée !");
                                                IP = 1;
                                                scan.close();
                                                break;
                                            }
                                            else
                                            {
                                                read("Vrai mot de passe : " + realmdp);
                                                socket.send(new DatagramPacket("3".getBytes(), "3".length(), packet.getAddress(), packet.getPort()));
                                                read("Connexion échouée et IP non changée (mauvais mot de passe) !");
                                                IP = 1;
                                                scan.close();
                                                break;
                                            }
                                        }
                                        scan.close();
                                    }
                            if(IP != 1)
                            {
                                send = new DatagramPacket("2".getBytes(), "2".length(), packet.getAddress(), packet.getPort());
                                socket.send(send);
                                read("Connexion sans compte !");
                            }
                        }
                    }
                    else if(type.equals("S"))
                    {
                        if(ip.equals(InetAddress.getByName("altiscraft.fr").getHostAddress()))
                        {
                            read(minecraftServer + "Connexion...");
                            read(minecraftServer + "Pseudo = " + info0 + " IP = " + info1);
                            File connectFile = new File(path + File.separatorChar + "Data" + File.separatorChar + info0 + "!" + info1 + ".txt");
                            if(connectFile.exists())
                            {
                                Scanner scan = new Scanner(connectFile);
                                if(scan.hasNextLine())
                                    realPassword = scan.nextLine();
                                read(minecraftServer + "Vrai mot de passe : " + realPassword);
                                socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                                read(minecraftServer + "Connexion réussie !");
                                scan.close();
                            }
                            else
                            {
                                socket.send(new DatagramPacket("0".getBytes(), "0".length(), packet.getAddress(), packet.getPort()));
                                read(minecraftServer + "Connexion échouée (fichier inexistant) !");
                            }
                        }
                        else
                            read(fatalAlert + "Tentative d'un utilisateur de simuler une anthentification serveur Minecraft !");
                    }
                    else if(type.equals("O"))
                        if(ip.equals(InetAddress.getByName("altiscraft.fr").getHostAddress()))
                        {
                            socket.send(new DatagramPacket("1".getBytes(), "1".length(), packet.getAddress(), packet.getPort()));
                            if(!restarting)
                            {
                                read(minecraftServer + "Crash, attente de " + info0 + " ms...");
                                new Timer().schedule(new TimerTask()
                                {
                                    @Override
                                    public void run()
                                    {
                                        read(minecraftServer + "Relancement...");
                                        try
                                        {
                                            ProcessBuilder processBuilder = new ProcessBuilder(directory + "forcescript.sh");
                                            processBuilder.directory(new File(directory));
                                            processBuilder.inheritIO();
                                            processBuilder.start();
                                        }
                                        catch(Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }, Integer.parseInt(info0));
                            }
                            else
                                read(minecraftServer + "Relancement en cours, packet répondu mais action non répété !");
                        }
                        else
                            read(fatalAlert + "Tentative d'un utilisateur de simuler un crash serveur Minecraft !");
                    /*
                     * else if(type.equals("PHP")) { if(info0.equalsIgnoreCase("localhost")) info0 =
                     * ip; File temp = new File(ts.replace("TS.php", "") + "temp.sh"); FileWriter fw
                     * = new FileWriter(temp);
                     * fw.write("#!/bin/bash\nsed -i '6i\\\"tsip\\\" => \\\"" + info0 + "\\\",' " +
                     * ts); fw.write("\nsed -i '7i\\\"tsport\\\" => \\\"" + info1 + "\\\",' " + ts);
                     * fw.write("\nsed -i '8i\\\"ts_query_admin\\\" => \\\"" + parts[3] + "\\\",' "
                     * + ts); fw.write("\nsed -i '9i\\\"ts_query_password\\\" => \\\"" + parts[4] +
                     * "\\\",' " + ts); fw.write("\nsed -i '10i\\\"ts_query_port\\\" => \\\"" +
                     * parts[5] + "\\\",' " + ts);
                     * fw.write("\nsed -i '11i\\\"ts_query_user_nick\\\" => \\\"" + parts[6] +
                     * "\\\");' " + ts); String command = ""; for(int j = 7; j < parts.length; j++)
                     * command += parts[j] + " "; fw.write("\nsed -i '15i\\" + command + "' " + ts);
                     * fw.write("\nphp " + ts); fw.close(); ProcessBuilder p = new
                     * ProcessBuilder(temp.getAbsolutePath()); BufferedReader reader = new
                     * BufferedReader(new InputStreamReader(p.start().getInputStream()));
                     * StringBuilder builder = new StringBuilder(); String line = null; while((line
                     * = reader.readLine()) != null) builder.append(line + File.separatorChar);
                     * String message = builder.toString(); read(message); socket.send(new
                     * DatagramPacket(message.getBytes(), message.length(), packet.getAddress(),
                     * packet.getPort())); fw = new FileWriter(temp);
                     * fw.write("#!/bin/bash\nsed -i -e '6,11d' " + ts);
                     * fw.write("\nsed -i -e '9d' " + ts); fw.close(); p = new
                     * ProcessBuilder(temp.getAbsolutePath()); p.start(); } else
                     * if(type.equals("Accessories")) { ArrayList<String> keys = new
                     * ArrayList<String>(); Scanner scan = new Scanner(accessories);
                     * while(scan.hasNextLine()) keys.add(scan.nextLine()); scan.close();
                     * if(info0.equals("check")) { boolean checked = false; for(int j = 0; j <
                     * keys.size(); j++) if(keys.get(j).equals(info1 + " " + info2.replaceAll(" ",
                     * "_"))) { socket.send(new DatagramPacket("1".getBytes(), "1".length(),
                     * packet.getAddress(), packet.getPort())); checked = true;
                     * read("Accessoire autorisé !"); break; } if(!checked) { socket.send(new
                     * DatagramPacket("0".getBytes(), "0".length(), packet.getAddress(),
                     * packet.getPort())); read("Accessoire refusé !"); } } else
                     * if(info0.equals("define")) { accessories.delete(); FileWriter fw = new
                     * FileWriter(accessories); for(int j = 0; j < keys.size(); j++)
                     * fw.write(keys.get(j) + "\n"); fw.write(info1 + " " + info2); fw.close();
                     * read("Accessoire défini !"); } }
                     */
                }
                else

                    read("IP non sécurisée !");
                socket.close();
            }
            catch(

            Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    static boolean isSafeIP(String ipToTest)
    {
        ArrayList<String> ips = ipsNotSafe();
        for(int ipIndex = 0; ipIndex < ips.size(); ipIndex++)
            if(ipToTest.contains(ips.get(ipIndex)))
                return false;
        return true;
    }

    static ArrayList<String> ipsNotSafe()
    {
        ArrayList<String> ipsNotSafe = new ArrayList<String>();
        try
        {
            Scanner scan = new Scanner(ipsNotSafeFile);
            while(scan.hasNextLine())
                ipsNotSafe.add(scan.nextLine());
            scan.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ipsNotSafe;
    }

    public static void read(Object object, Object prefix)
    {
        readZB(prefix.toString() + " " + object.toString());
    }

    public static String getDate()
    {
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + ":" + c.get(Calendar.MILLISECOND) + " ";
        if(c.get(Calendar.AM_PM) == 0)
            date += "AM";
        else
            date += "PM";
        return date;
    }

    public static void writeZB(Object object)
    {
        String messageWithDate = getDate() + " : " + object;
        writeLogZB(messageWithDate);
    }

    public static void writeZBA(Object object)
    {
        String messageWithDate = getDate() + " : " + object;
        writeLogZBA(messageWithDate);
    }

    public static void readZB(Object object)
    {
        String messageWithDate = getDate() + " : " + object;
        System.out.println(messageWithDate);
        writeLogZB(messageWithDate);
    }

    public static void read(Object object)
    {
        String messageWithDate = getDate() + " : " + object;
        writeLog(messageWithDate);
    }

    public static void writeLogZB(String message)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(logZB, true));
            out.write(message + "\n");
            out.flush();
            out.close();
        }
        catch(Exception e)
        {}
    }

    public static void writeLogZBA(String message)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(logZBA, true));
            out.write(message + "\n");
            out.flush();
            out.close();
        }
        catch(Exception e)
        {}
    }

    public static void writeLog(String message)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(log, true));
            out.write(message + "\n");
            out.flush();
            out.close();
        }
        catch(Exception e)
        {}
    }

    public static String needZero(int i)
    {
        String a = Integer.toString(i);
        if(i < 10)
            a = 0 + a;
        return a;
    }
}
