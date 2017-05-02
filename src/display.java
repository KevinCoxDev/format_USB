//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.Color.RED;

public class display {

    private ArrayList<String> rules;
    private JButton button1;
    private JButton button2;
    JPanel comboPanel;
    JPanel listPanel;
    JFrame guiFrame;
    JProgressBar progressBar;
    JComboBox list1;

    public static void main(String[] args) {

        new display();
    }

    public display()
    {
        guiFrame = new JFrame();

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Multi-USB reformatter");
        guiFrame.setSize(500,150);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //Options for the JComboBox
        Object[] models =  callPowershell.generateDeviceList(callPowershell.make_map(callPowershell.parse_file()));
        String[] models_1 = Arrays.asList(models).toArray(new String[models.length]);

        //Options for the JList
        rules = new ArrayList<String>();
        rules.add("This tool formats EVERY usb type device");
        rules.add("Any still connected will lose all data");
        //String rules1 = ();

        //The first JPanel contains a JLabel and JCombobox
        comboPanel = new JPanel();
        JLabel comboLbl = new JLabel("Please select the nas model you wish to use this for NAS Model:");
        list1 = new JComboBox(models_1);
        list1.addActionListener(this::actionPerformed);

        comboPanel.add(comboLbl);
        comboPanel.add(list1);

        //Create the second JPanel. Add a JLabel and JList and
        //make use the JPanel is not visible.
        listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel listLbl = new JLabel("WARNING! :Please disconnect EVERY usb you do not wish to format");
        listLbl.setForeground(RED);
        JLabel ruleLbl = new JLabel(rules.toString());
        //ruleLbl.setLayoutOrientation(JLabel.HORIZONTAL_WRAP);

        listPanel.add(listLbl);
        listPanel.add(ruleLbl);

        button1 = new JButton( "Continue");
        button1.addActionListener(this::actionPerformed);
        button2 = new JButton( "Format");

        guiFrame.add(comboPanel, BorderLayout.NORTH);
        guiFrame.add(listPanel, BorderLayout.CENTER);
        guiFrame.add(button1,BorderLayout.SOUTH);
        //make sure the JFrame is visible
        guiFrame.setVisible(true);


    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == button1) {
            listPanel.setVisible(!listPanel.isVisible());
            comboPanel.setVisible(!comboPanel.isVisible());
            //button1.setVisible(!button1.isVisible())
            button2 = button1;
            button1 = null;
            button2.setText("FORMAT");

        }
        else if(button2 == e.getSource()) {
            try {
                //rules.clear();
                JOptionPane.showMessageDialog(null, "Process will start when you press OK. \nPlease do not disconnect any device. \nYou will be notified once the process ends. \n", "alert", JOptionPane.INFORMATION_MESSAGE);
                //rules.clear();
                //rules.add("Drives are formatting");
                callPowershell.powerShellMount();
                JOptionPane.showMessageDialog(null, "Drives have been prepared, Thank You", "alert", JOptionPane.PLAIN_MESSAGE);
                guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(list1 == e.getSource()) {
            String str = (String) list1.getSelectedItem();
            callPowershell.select_file(callPowershell.make_map(callPowershell.parse_file()),str);
        }
    }



}

