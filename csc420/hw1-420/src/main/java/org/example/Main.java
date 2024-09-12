package org.example;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener{

    JLabel picture;

    public Main(){
        super(new MigLayout());
        String[] countryStrings = {"Albania", "Andorra", "Austria", "Belarus",
        "Belgium", "Bosnia and Herzegovina", "Bulgaria",
        "Croatia", "Czech Republic", "Denmark", "Estonia", "Finland",
        "France", "Georgia", "Germany", "Greece", "Hungary", "Iceland",
        "Ireland", "Italy", "Latvia", "Liechtenstein", "Lithuania",
        "Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands",
        "North Macedonia", "Norway", "Poland", "Portugal", "Romania", "Russia",
        "San Marino", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden", "Switzerland",
        "Ukraine", "United Kingdom"};
        JComboBox<String> countryList = new JComboBox<String>(countryStrings);
        countryList.setSelectedIndex(0);
        countryList.addActionListener(this);

        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.RIGHT);
        updateLabel(countryStrings[countryList.getSelectedIndex()]);
        countryList.setPreferredSize(new Dimension(150, 20));
        picture.setPreferredSize(new Dimension(450, 300));

        add(countryList, "cell 0 1");
        add(picture, "grow, span");
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public void actionPerformed(ActionEvent e){
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        String countryName = (String)cb.getSelectedItem();
        updateLabel(countryName);
    }

    protected void updateLabel(String name){
        ImageIcon icon = createImageIcon("src/main/resources/images/" + name + ".jpg");
        picture.setIcon(icon);
        picture.setToolTipText("The flag of " + name);
    }

    protected static ImageIcon createImageIcon(String path){

        if (path != null){
            ImageIcon imgIcon = new ImageIcon(path);
            java.awt.Image img = imgIcon.getImage();
            java.awt.Image resized = img.getScaledInstance(450, 300, Image.SCALE_SMOOTH);
            return new ImageIcon(resized);
        }
        else {
            System.out.println("no file found: " + path);
            return null;
        }
    }
    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Vexillologist's Paradise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent contentPane = new Main();
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createAndShowGUI();
            }
        });
    }
}