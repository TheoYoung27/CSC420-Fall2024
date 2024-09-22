package org.example;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener {

    boolean showingCircle = false;
    JComboBox<String> colorList;
    String[] colorNames = {"Flying Monkey Blue", "Emerald Green", "Yellow Brick Road Yellow",
            "Ruby Slippers Red", "Tin Man Silver", };
    String[] colorCodes = {"#67adc2", "#50C878", "#FFFF00", "#9b111e", "#C0C0C0"};
    JSlider vtclSlider;
    JSlider hztlSlider;
    CanvasPanel canvas;
    public Main(){
        super(new MigLayout());
        setBackground(Color.GRAY);
        colorList = new JComboBox<>(colorNames);
        colorList.setSelectedIndex(0);
        colorList.addActionListener(this);
        JButton button = new JButton("Show");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!showingCircle){
                    button.setText("Hide");
                    showingCircle = true;
                }
                else {
                    button.setText("Show");
                    showingCircle = false;
                }
                repaint();
            }
        });
        JLabel bg = new JLabel();
        canvas = new CanvasPanel();
        canvas.setPreferredSize(new Dimension(300, 200));
        vtclSlider = new JSlider(JSlider.VERTICAL,0,200,0);
        vtclSlider.setPreferredSize(new Dimension(15, 214));
        hztlSlider = new JSlider(JSlider.HORIZONTAL, 0, 300, 0);
        hztlSlider.setPreferredSize(new Dimension(314, 15));
        add(bg);
        add(colorList, "cell 0 1");
        add(button, "cell 0 1");
        add(vtclSlider, "cell 0 2, growy");
        add(canvas, "cell 0 2");
        add(hztlSlider, "cell 0 3, gapx 15");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        canvas.repaint();
    }

    private class CanvasPanel extends JPanel{
        public CanvasPanel(){
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            if(showingCircle){
                g.setColor(Color.decode(colorCodes[colorList.getSelectedIndex()]));
                int diameter = 10;
                int x = hztlSlider.getValue()  - diameter / 2;
                int y = canvas.getHeight() - vtclSlider.getValue() - diameter / 2;
                g.fillOval(x, y, diameter, diameter);
            }
        }
    }


    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Extremely Useful Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent contentPane = new Main();
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createAndShowGUI();
            }
        });
    }
}