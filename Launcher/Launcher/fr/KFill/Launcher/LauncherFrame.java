package fr.KFill.Launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame
{
    public static LauncherFrame instance;
    public LauncherPanel launcherPanel;
    private static CrashReporter crashReporter;
    public LauncherFrame()
    {
        setTitle("AltisCraft.fr");
        setSize(975, 625);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setIconImage(Swinger.getResource("icon.png"));
        setContentPane(launcherPanel = new LauncherPanel());
        WindowMover mover = new WindowMover(this);
        addMouseListener(mover);
        addMouseMotionListener(mover);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/fr/KFill/Launcher/ressources/");
        Launcher.LC_CRASHES_DIR.mkdirs();
        crashReporter = new CrashReporter("AltisCraft.fr", Launcher.LC_CRASHES_DIR);
        instance = new LauncherFrame();
    }

    public static LauncherFrame getInstance()
    {
        return instance;
    }

    public LauncherPanel getLauncherPanel()
    {
        return launcherPanel;
    }

    public static CrashReporter getCrashReporter()
    {
        return crashReporter;
    }
}