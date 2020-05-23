package fr.KFill.Launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener, KeyListener
{
    private Saver saver = new Saver(new File(Launcher.LC_DIR, "launcher.properties"));
    private JTextField nameField = new JTextField(saver.get("Family Name")), familyNameField = new JTextField(saver.get("Name"));
    String playingName = "", lign = "";
    File familyFile = new File(Launcher.LC_INFOS.getGameDir() + "\\Family Name.txt"), nameFile = new File(Launcher.LC_INFOS.getGameDir() + "\\Name.txt");
    private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png")), quitButton = new STexturedButton(Swinger.getResource("quit.png")), hideButton = new STexturedButton(Swinger.getResource("hide.png"));
    private SColoredBar progressBar = new SColoredBar(new Color(0, 255, 0, 70), new Color(255, 0, 0, 70));
    private JLabel infoLabel = new JLabel("Cliquez sur jouer !", SwingConstants.CENTER);

    public LauncherPanel()
    {
        try
        {
            setFocusable(true);
            requestFocusInWindow();
            addKeyListener(this);
            setLayout(null);
            if(familyFile.exists())
            {
                Scanner sc = new Scanner(familyFile);
                if(sc.hasNextLine())
                    familyNameField.setText(sc.nextLine());
                sc.close();
            }
            else
            {
                FileWriter fw = new FileWriter(familyFile);
                fw.write("");
                fw.close();
            }
            if(nameFile.exists())
            {
                Scanner sc = new Scanner(nameFile);
                if(sc.hasNextLine())
                    nameField.setText(sc.nextLine());
                sc.close();
            }
            else
            {
                FileWriter fw = new FileWriter(nameFile);
                fw.write("");
                fw.close();
            }

            nameField.setForeground(Color.WHITE);
            nameField.setCaretColor(Color.WHITE);
            nameField.setFont(nameField.getFont().deriveFont(25F));
            nameField.setOpaque(false);
            nameField.setBorder(null);
            nameField.setBounds(640, 90, 300, 42);
            add(nameField);

            familyNameField.setForeground(Color.WHITE);
            familyNameField.setCaretColor(Color.WHITE);
            familyNameField.setFont(familyNameField.getFont().deriveFont(25F));
            familyNameField.setOpaque(false);
            familyNameField.setBorder(null);
            familyNameField.setBounds(640, 200, 300, 42);
            add(familyNameField);

            playButton.setBounds(640, 250, 300, 42);
            playButton.addEventListener(this);
            add(playButton);

            quitButton.setBounds(920, 2, 25, 25);
            quitButton.addEventListener(this);
            add(quitButton);

            hideButton.setBounds(900, 2, 25, 25);
            hideButton.addEventListener(this);
            add(hideButton);

            progressBar.setBounds(11, 592, 953, 20);
            add(progressBar);

            infoLabel.setForeground(Color.blue);
            infoLabel.setBounds(11, 592, 953, 20);
            add(infoLabel);
        }
        catch(Exception e)
        {}
    }

    public void onEvent(SwingerEvent e)
    {
        if(e.getSource() == playButton)
            play();
        else if(e.getSource() == quitButton)
            Animator.fadeOutFrame(LauncherFrame.getInstance(), 3, new Runnable()
            {
                public void run()
                {
                    LauncherFrame.instance.setVisible(false);
                    System.exit(0);
                }
            });
        else if(e.getSource() == hideButton)
            LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(Swinger.getResource("background.png"), 0, 0, getWidth(), getHeight(), this);
    }

    private void setFieldsEnabled(boolean enabled)
    {
        nameField.setEnabled(enabled);
        familyNameField.setEnabled(enabled);
        playButton.setEnabled(enabled);
    }

    public SColoredBar getProgressBar()
    {
        return progressBar;
    }

    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }

    public void keyPressed(KeyEvent event)
    {
        if(event.getKeyCode() == 10)
            play();
    }

    public void play()
    {
        setFieldsEnabled(false);
        if(nameField.getText().startsWith("-") || nameField.getText().endsWith("-"))
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez ne pas mettre de - au début ou/et à la fin de votre prénom.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
		//////////// COMMENTAIRES FAIT EN MAI 2020: Ce code est un des premiers que j'ai écrit. Effectivement ce code est très mal élaboré.
        nameField.setText(nameField.getText().replace(" ", ""));
        nameField.setText(nameField.getText().replace("1", ""));
        nameField.setText(nameField.getText().replace("2", ""));
        nameField.setText(nameField.getText().replace("3", ""));
        nameField.setText(nameField.getText().replace("4", ""));
        nameField.setText(nameField.getText().replace("5", ""));
        nameField.setText(nameField.getText().replace("6", ""));
        nameField.setText(nameField.getText().replace("7", ""));
        nameField.setText(nameField.getText().replace("8", ""));
        nameField.setText(nameField.getText().replace("9", ""));
        nameField.setText(nameField.getText().replace("0", ""));
        nameField.setText(nameField.getText().replace(".", ""));
        nameField.setText(nameField.getText().replace("_", ""));
        nameField.setText(nameField.getText().replace(",", ""));
        nameField.setText(nameField.getText().replace("@", ""));
        nameField.setText(nameField.getText().replace("â", "a"));
        nameField.setText(nameField.getText().replace("à", "a"));
        nameField.setText(nameField.getText().replace("ä", "a"));
        nameField.setText(nameField.getText().replace("é", "e"));
        nameField.setText(nameField.getText().replace("è", "e"));
        nameField.setText(nameField.getText().replace("ê", "e"));
        nameField.setText(nameField.getText().replace("ë", "e"));
        nameField.setText(nameField.getText().replace("ô", "o"));
        nameField.setText(nameField.getText().replace("ö", "o"));
        nameField.setText(nameField.getText().replace("î", "i"));
        nameField.setText(nameField.getText().replace("ï", "i"));
        nameField.setText(nameField.getText().replace("<", ""));
        nameField.setText(nameField.getText().replace(">", ""));
        nameField.setText(nameField.getText().replace("!", ""));
        nameField.setText(nameField.getText().replace("?", ""));
        nameField.setText(nameField.getText().replace("*", ""));
        nameField.setText(nameField.getText().replace("€", ""));
        nameField.setText(nameField.getText().replace("$", ""));
        nameField.setText(nameField.getText().replace("£", ""));
        nameField.setText(nameField.getText().replace(":", ""));
        nameField.setText(nameField.getText().replace(";", ""));
        nameField.setText(nameField.getText().replace("/", ""));
        nameField.setText(nameField.getText().replace("\\\\", ""));
        nameField.setText(nameField.getText().replace("^", ""));
        nameField.setText(nameField.getText().replace("%", ""));
        nameField.setText(nameField.getText().replace("[", ""));
        nameField.setText(nameField.getText().replace("]", ""));
        nameField.setText(nameField.getText().replace("{", ""));
        nameField.setText(nameField.getText().replace("}", ""));
        nameField.setText(nameField.getText().replace("(", ""));
        nameField.setText(nameField.getText().replace(")", ""));
        nameField.setText(nameField.getText().replace("²", ""));
        nameField.setText(nameField.getText().replace("-", ""));
        nameField.setText(nameField.getText().replace("'", ""));
        nameField.setText(nameField.getText().replace("=", ""));
        nameField.setText(nameField.getText().replace("+", ""));
        nameField.setText(nameField.getText().replace("•", ""));
        nameField.setText(nameField.getText().replace("§", ""));
        nameField.setText(nameField.getText().replace("°", ""));
        nameField.setText(nameField.getText().replace("~", ""));
        nameField.setText(nameField.getText().replace("#", ""));
        nameField.setText(nameField.getText().replace("\t", ""));
        nameField.setText(nameField.getText().toLowerCase());
        nameField.setText(nameField.getText().replaceFirst(".", (nameField.getText().charAt(0) + "").toUpperCase()));
        if(familyNameField.getText().startsWith("-") || familyNameField.getText().endsWith("-"))
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez ne pas mettre de - au début ou/et à la fin de votre nom.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
        familyNameField.setText(familyNameField.getText().replace(" ", ""));
        familyNameField.setText(familyNameField.getText().replace("1", ""));
        familyNameField.setText(familyNameField.getText().replace("2", ""));
        familyNameField.setText(familyNameField.getText().replace("3", ""));
        familyNameField.setText(familyNameField.getText().replace("4", ""));
        familyNameField.setText(familyNameField.getText().replace("5", ""));
        familyNameField.setText(familyNameField.getText().replace("6", ""));
        familyNameField.setText(familyNameField.getText().replace("7", ""));
        familyNameField.setText(familyNameField.getText().replace("8", ""));
        familyNameField.setText(familyNameField.getText().replace("9", ""));
        familyNameField.setText(familyNameField.getText().replace("0", ""));
        familyNameField.setText(familyNameField.getText().replace(".", ""));
        familyNameField.setText(familyNameField.getText().replace("_", ""));
        familyNameField.setText(familyNameField.getText().replace(",", ""));
        familyNameField.setText(familyNameField.getText().replace("@", ""));
        familyNameField.setText(familyNameField.getText().replace("â", "a"));
        familyNameField.setText(familyNameField.getText().replace("à", "a"));
        familyNameField.setText(familyNameField.getText().replace("ä", "a"));
        familyNameField.setText(familyNameField.getText().replace("é", "e"));
        familyNameField.setText(familyNameField.getText().replace("è", "e"));
        familyNameField.setText(familyNameField.getText().replace("ê", "e"));
        familyNameField.setText(familyNameField.getText().replace("ë", "e"));
        familyNameField.setText(familyNameField.getText().replace("ô", "o"));
        familyNameField.setText(familyNameField.getText().replace("ö", "o"));
        familyNameField.setText(familyNameField.getText().replace("î", "i"));
        familyNameField.setText(familyNameField.getText().replace("ï", "i"));
        familyNameField.setText(familyNameField.getText().replace("<", ""));
        familyNameField.setText(familyNameField.getText().replace(">", ""));
        familyNameField.setText(familyNameField.getText().replace("!", ""));
        familyNameField.setText(familyNameField.getText().replace("?", ""));
        familyNameField.setText(familyNameField.getText().replace("*", ""));
        familyNameField.setText(familyNameField.getText().replace("€", ""));
        familyNameField.setText(familyNameField.getText().replace("$", ""));
        familyNameField.setText(familyNameField.getText().replace("£", ""));
        familyNameField.setText(familyNameField.getText().replace(":", ""));
        familyNameField.setText(familyNameField.getText().replace(";", ""));
        familyNameField.setText(familyNameField.getText().replace("/", ""));
        familyNameField.setText(familyNameField.getText().replace("\\\\", ""));
        familyNameField.setText(familyNameField.getText().replace("ç", ""));
        familyNameField.setText(familyNameField.getText().replace("%", ""));
        familyNameField.setText(familyNameField.getText().replace("[", ""));
        familyNameField.setText(familyNameField.getText().replace("]", ""));
        familyNameField.setText(familyNameField.getText().replace("{", ""));
        familyNameField.setText(familyNameField.getText().replace("}", ""));
        familyNameField.setText(familyNameField.getText().replace("(", ""));
        familyNameField.setText(familyNameField.getText().replace(")", ""));
        familyNameField.setText(familyNameField.getText().replace("²", ""));
        familyNameField.setText(familyNameField.getText().replace("-", ""));
        familyNameField.setText(familyNameField.getText().replace("'", ""));
        familyNameField.setText(familyNameField.getText().replace("=", ""));
        familyNameField.setText(familyNameField.getText().replace("+", ""));
        familyNameField.setText(familyNameField.getText().replace("•", ""));
        familyNameField.setText(familyNameField.getText().replace("§", ""));
        familyNameField.setText(familyNameField.getText().replace("°", ""));
        familyNameField.setText(familyNameField.getText().replace("~", ""));
        familyNameField.setText(familyNameField.getText().replace("#", ""));
        familyNameField.setText(familyNameField.getText().replace("\t", ""));
        familyNameField.setText(familyNameField.getText().toLowerCase());
        familyNameField.setText(familyNameField.getText().replaceFirst(".", (familyNameField.getText().charAt(0) + "").toUpperCase()));

        playingName = nameField.getText() + "_" + familyNameField.getText();

        if(nameField.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un prénom.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
        if(nameField.getText().length() == 1)
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un prénom à au moins 2 caractères.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
        if(familyNameField.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un nom.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
        if(familyNameField.getText().length() == 1)
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un nom à au moins 2 caractères.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
        if(playingName.length() > 15)
        {
            JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un nom et prénom avec une longueur inférieur ou égale à 15.", "Error", JOptionPane.ERROR_MESSAGE);
            setFieldsEnabled(true);
            return;
        }
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Launcher.auth(playingName, playingName);
                }
                catch(AuthenticationException e)
                {
                    JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de se connecter : " + e.getErrorModel().getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    setFieldsEnabled(true);
                    return;
                }
                try
                {
                    FileWriter fw = new FileWriter(familyFile);
                    fw.write(familyNameField.getText());
                    fw.close();
                }
                catch(Exception e)
                {}
                try
                {
                    FileWriter fw = new FileWriter(nameFile);
                    fw.write(nameField.getText());
                    fw.close();
                }
                catch(Exception e)
                {}
                try
                {
                    Launcher.update();
                }
                catch(Exception e)
                {
                    Launcher.interruptThread();
                    LauncherFrame.getCrashReporter().catchError(e, "Erreur, impossible de mettre Minecraft à jour !");
                    setFieldsEnabled(true);
                    return;
                }
                try
                {
                    Launcher.launch();
                }
                catch(LaunchException e)
                {
                    LauncherFrame.getCrashReporter().catchError(e, "Erreur, impossible de lancer Minecraft !");
                    setFieldsEnabled(true);
                }
                catch(Exception e)
                {}
            }
        };
        t.start();
    }

    public void keyReleased(KeyEvent e)
    {}

    public void keyTyped(KeyEvent e)
    {}
}