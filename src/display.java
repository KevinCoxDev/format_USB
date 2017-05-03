//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.Color.blue;

public class display {

    static JButton drive1;
    static JButton drive2;
    static JButton drive3;
    static JButton drive4;
    static JButton drive5;

    private ArrayList<String> rules;
    private JButton button1;
    JPanel comboPanel;
    JPanel listPanel;
    JFrame guiFrame;
    JComboBox list1;;

    public static void main(String[] args) {

        new display();
    }

    public display()
    {
        guiFrame = new JFrame();

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Multi-USB reformatter");
        guiFrame.setSize(500,200);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //Options for the JComboBox
        Object[] models =  callPowershell.generateDeviceList(callPowershell.make_map(callPowershell.parse_file()));
        String[] models_1 = Arrays.asList(models).toArray(new String[models.length]);

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
        listPanel.setBackground(blue);

        //ruleLbl.setLayoutOrientation(JLabel.HORIZONTAL_WRAP);

        button1 = new JButton( "Format");
        button1.addActionListener(this::actionPerformed);

        guiFrame.add(comboPanel, BorderLayout.NORTH);
        guiFrame.add(listPanel, BorderLayout.CENTER);
        guiFrame.add(button1,BorderLayout.SOUTH);
        //make sure the JFrame is visible
        guiFrame.setVisible(true);

        BoxLayout boxlayout = new BoxLayout(listPanel, BoxLayout.X_AXIS);
        listPanel.setLayout(new GridLayout(1,5));
        listPanel.setBorder(new EmptyBorder(new Insets(20, 50, 20, 50)));


        drive2 = new JButton(" ");
        listPanel.add(drive2);
        drive2.setVisible(true);
        drive2.setBackground(Color.lightGray);

        drive3 = new JButton(" ");
        listPanel.add(drive3);
        drive3.setVisible(true);
        drive3.setBackground(Color.lightGray);

        drive4 = new JButton(" ");
        listPanel.add(drive4);
        drive4.setVisible(true);
        drive4.setBackground(Color.lightGray);

        drive5 = new JButton(" ");
        listPanel.add(drive5);
        drive5.setVisible(true);
        drive5.setBackground(Color.lightGray);

        drive1 = new JButton(" ");
        listPanel.add(drive1);
        drive1.setVisible(true);
        drive1.setBackground(Color.lightGray);



    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == button1) {
            listPanel.setVisible(!listPanel.isVisible());
            comboPanel.setVisible(!comboPanel.isVisible());
            button1.setVisible(!button1.isVisible());
            JOptionPane.showMessageDialog(null, "WARNING!\nPlease do not disconnect any device.\nProcess will start when you press OK.\nYou will be notified once the process ends. \n", "alert", JOptionPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            try {
                callPowershell.powerShellMount();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Drives have been prepared, Thank You", "alert", JOptionPane.PLAIN_MESSAGE);
            guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
        else if(list1 == e.getSource()) {
            String str = (String) list1.getSelectedItem();
            callPowershell.select_file(callPowershell.make_map(callPowershell.parse_file()),str);
        }
    }



}

