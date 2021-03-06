import javax.print.attribute.standard.Destination;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class LoLanguage extends JFrame
{
    private JPanel m_appPanel;
    private JButton m_execute;
    private JComboBox m_serverName;
    private JComboBox m_language;
    private JButton m_launcherButton;
    private JLabel m_folderText;

    private File m_launcher;
    private File m_launcherSettings;
    private File m_system;

    public LoLanguage(String title, int width, int height)
    {
        super(title);

        this.setPreferredSize(new Dimension(width,height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(m_appPanel);
        this.pack();

        createUIComponents();
    }

    public static void main(String[] args)
    {
        JFrame frame = new LoLanguage("LoLanguage", 700, 700);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        m_serverName.addItem(Item("EU West", "EUW"));
        m_serverName.addItem(Item("EU Nordic East", "EUNE"));
        m_serverName.addItem(Item("North America", "NA"));
        m_serverName.addItem(Item("Turkey", "TR"));
        m_serverName.addItem(Item("Brazil", "BR"));
        m_serverName.addItem(Item("Russia", "RU"));
        m_serverName.addItem(Item("Latin America North", "LA1"));
        m_serverName.addItem(Item("Latin America South", "LA2"));
        m_serverName.addItem(Item("Japan", "JP"));
        m_serverName.addItem(Item("Oceania", "OC1"));

        m_language.addItem(Item("English GB", "en_GB"));
        m_language.addItem(Item("English US", "en_US"));
        m_language.addItem(Item("English Australia", "en_AU"));
        m_language.addItem(Item("Italian", "it_IT"));
        m_language.addItem(Item("Korean", "ko_KR"));
        m_language.addItem(Item("Japanese", "ja_JP"));
        m_language.addItem(Item("French", "fr_FR"));
        m_language.addItem(Item("Turkish", "tr_TR"));
        m_language.addItem(Item("Russian", "ru_RU"));
        m_language.addItem(Item("Romanian", "ro_RO"));
        m_language.addItem(Item("Portuguese", "pt_BR"));
        m_language.addItem(Item("Polish", "pl_PL"));
        m_language.addItem(Item("Hungarian", "hu_HU"));
        m_language.addItem(Item("General Spanish", "es_MX"));
        m_language.addItem(Item("Spain Spanish", "es_ES"));
        m_language.addItem(Item("Argentina Spanish", "es_AR"));
        m_language.addItem(Item("Greek", "el_GR"));
        m_language.addItem(Item("German", "de_DE"));
        m_language.addItem(Item("Czech", "cs_CZ"));
        m_language.addItem(Item("Philippine English", "en_PH"));
        m_language.addItem(Item("Singapore English", "en_SG"));
        m_language.addItem(Item("China Chinese", "zh_CN"));
        m_language.addItem(Item("Taiwan Chinese", "zh_TW"));
        m_language.addItem(Item("Malaysia Chinese", "zh_MY"));
        m_language.addItem(Item("Malay", "ms_MY"));
        m_language.addItem(Item("Vietnamese", "vn_VN"));
        m_language.addItem(Item("Thai", "th_TH"));

        m_execute.setBorder(new EmptyBorder(0,0,0,0));

        m_launcherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Riot Games folder");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setCurrentDirectory(new File("C:"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.showOpenDialog(m_appPanel);

                String launcherDir = "\\League of Legends\\LeagueClient.exe";
                String systemDir = "\\League of Legends\\system.yaml";
                String clientDir = "\\League of Legends\\Config\\LeagueClientSettings.yaml";

                String dir = fileChooser.getSelectedFile().getAbsolutePath();

                m_launcher = new File(dir + launcherDir);
                m_system = new File(dir + systemDir);
                m_launcherSettings = new File(dir + clientDir);

                if(dir.contains("Riot Games"))
                {
                    m_folderText.setText("RIOT GAMES FOLDER SELECTED");
                    m_folderText.setForeground(Color.green);
                    m_serverName.setEnabled(true);
                    m_language.setEnabled(true);
                    m_execute.setEnabled(true);
                    ReplaceInFile(m_launcherSettings.getAbsolutePath(),0);
                    ReplaceInFile(m_launcherSettings.getAbsolutePath(), 1);
                    ReplaceInFile(m_system.getAbsolutePath(), 2);
                }
            }
        });

        m_serverName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReplaceInFile(m_launcherSettings.getAbsolutePath(),0);
            }
        });

        m_language.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReplaceInFile(m_launcherSettings.getAbsolutePath(), 1);
                ReplaceInFile(m_system.getAbsolutePath(), 2);
            }
        });

        m_execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectableItem server = (SelectableItem)m_serverName.getSelectedItem();
                SelectableItem language = (SelectableItem)m_language.getSelectedItem();

                Desktop desktop = Desktop.getDesktop();
                try
                {
                    desktop.open(m_launcher);
                } catch (IOException ex)
                {

                }
            }
        });
    }

    private SelectableItem Item(String key, String value)
    {
        return new SelectableItem(key,value);
    }

    private void ReplaceInFile(String filePath, int type)
    {
        try
        {
            Scanner sc = new Scanner(new File(filePath));
            ArrayList<String> lines = new ArrayList<String>();

            while(sc.hasNextLine())
            {
                lines.add(sc.nextLine()+System.lineSeparator());
            }
            sc.close();

            String lineToFind = "";
            String replacementValue = "";

            switch(type)
            {
                case 0:
                    lineToFind = "region:";
                    replacementValue = ((SelectableItem)m_serverName.getSelectedItem()).GetValue();
                    break;
                case 1:
                    lineToFind = "locale:";
                    replacementValue = ((SelectableItem)m_language.getSelectedItem()).GetValue();
                    break;
                case 2:
                    lineToFind = "  " + ((SelectableItem)m_serverName.getSelectedItem()).GetValue() + ":";
                    replacementValue = ((SelectableItem)m_language.getSelectedItem()).GetValue();
            }

            String replaceFile = "";

            boolean found = false;
            boolean editedDefault = false;
            boolean editedAvailable = false;
            int foundAt = 0;
            int i = 0;
            for(String line : lines)
            {
                if(line.contains(lineToFind))
                {
                    foundAt = i;
                    found = true;
                    if(type < 2)
                    {
                        line = "        " + lineToFind + " \"" + replacementValue + "\"" + System.lineSeparator();
                    }
                }else if(type > 1 && line.contains("default_locale:") && found && i > foundAt && !editedAvailable)
                {
                    line = "    default_locale: " + ((SelectableItem)m_language.getSelectedItem()).GetValue() + System.lineSeparator();
                    editedAvailable = true;
                } else if (type > 1 && line.contains("available_locales:") && found && i > foundAt && !editedDefault)
                {
                    line = "    available_locales:" + System.lineSeparator() + "    - " + replacementValue + System.lineSeparator();
                    editedDefault = true;
                } else if(type == 1 && line.contains("locales:"))
                {
                    line = "        locales:" + System.lineSeparator() + "        - \"" + ((SelectableItem)m_language.getSelectedItem()).GetValue() + "\"" + System.lineSeparator();
                }
                replaceFile += line;
                i++;
            }

            FileOutputStream fileOut = new FileOutputStream(filePath);
            fileOut.write(replaceFile.getBytes());
            fileOut.close();
        } catch(Exception e)
        {

        }
    }
}
