package fr.KFill.bootstrap;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.bootstrap.Bootstrap;
import fr.theshark34.openlauncherlib.bootstrap.LauncherClasspath;
import fr.theshark34.openlauncherlib.bootstrap.LauncherInfos;
import fr.theshark34.openlauncherlib.util.ErrorUtil;
import fr.theshark34.openlauncherlib.util.GameDir;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

@SuppressWarnings("serial")
public class DCCBootstrap extends JFrame
{
    private static SplashScreen splash;
    private static SColoredBar bar;
    private static Thread barThread;
    private static final LauncherInfos LC_B_INFOS = new LauncherInfos("DCC", "fr.KFill.Launcher.LauncherFrame");
    private static final File LC_DIR = GameDir.createGameDir("DCC");
    private static final LauncherClasspath LC_B_CP = new LauncherClasspath(new File(LC_DIR, "Launcher/launcher.jar"), new File(LC_DIR, "Launcher/Libs/"));
    private static ErrorUtil errorUtil = new ErrorUtil(new File(LC_DIR, "Launcher/crashes/"));

    public static void main(String[] args)
    {
        Swinger.setResourcePath("/fr/KFill/bootstrap/resources/");
        displaySplash();
        try
        {
            doUpdate();
        }
        catch(Exception e)
        {
            errorUtil.catchError(e, "Impossible de mettre à jour le launcher !");
            barThread.interrupt();
        }
        try
        {
            launchLauncher();
        }
        catch(IOException e)
        {
            errorUtil.catchError(e, "Impossible de lancer le launcher !");
        }
    }

    private static void displaySplash()
    {
        splash = new SplashScreen("AltisCraft.fr", Swinger.getResource("splash.png"));
        splash.setLayout(null);
        bar = new SColoredBar(new Color(255, 255, 255, 15), new Color(255, 255, 255, 0));
        bar.setBounds(0, 40, 80, 0);
        splash.add(bar);
        splash.setVisible(true);
    }

    private static void doUpdate() throws Exception
    {
        barThread = new Thread()
        {
            @Override
            public void run()
            {
                while(!isInterrupted())
                {
                    bar.setValue((int)(BarAPI.getNumberOfTotalDownloadedBytes() / 1000));
                    bar.setMaximum((int)(BarAPI.getNumberOfTotalBytesToDownload() / 1000));
                }
            }
        };
        barThread.start();
        new SUpdate("http://altiscraft.fr/bootstrap/", new File(LC_DIR, "Launcher")).start();
        barThread.interrupt();
    }

    private static void launchLauncher() throws IOException
    {
        splash.setVisible(false);
        try
        {
            new Bootstrap(LC_B_CP, LC_B_INFOS).launch().waitFor();
        }
        catch(Exception e)
        {}
        System.exit(0);
    }
}