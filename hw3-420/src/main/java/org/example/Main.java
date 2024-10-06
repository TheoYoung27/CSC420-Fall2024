package org.example;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main extends JPanel implements ActionListener{
    String[] names = new String[100];
    ImageIcon[] pictures = new ImageIcon[100];
    JLabel picture;
    JProgressBar progressBar = new JProgressBar(0, 100);
    JLabel loading = new JLabel(new ImageIcon("src/main/resources/loading-7528_128.gif"));

    class worker extends SwingWorker<ImageIcon[], Integer>{
        @Override
        public ImageIcon[] doInBackground() {
            ImageIcon[] imgs = new ImageIcon[100];
            File dir = new File("src/main/resources/images");
            File[] dirListing = dir.listFiles();
            if(dirListing != null) {
                System.out.println(dirListing.length);
                for (int i = 0; i < dirListing.length; i++) {
                    System.out.println(i + " " + dirListing[i].getName().substring(
                            0, dirListing[i].getName().length()-4));
                    names[i] = dirListing[i].getName().substring(
                            0, dirListing[i].getName().length()-4);
                    imgs[i] = createImageIcon(dirListing[i].getAbsolutePath());
                    publish(i+1);
                    setProgress(i+1);
                }
            }
            System.out.println("done");
            return imgs;
        }
        protected void done(){
            try {
                pictures = get();
                remove(loading);
                remove(progressBar);
                JComboBox<String> nameList = new JComboBox<>(names);
                nameList.addActionListener(Main.this);
                picture = new JLabel();
                picture.setIcon(pictures[0]);
                picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
                picture.setHorizontalAlignment(JLabel.RIGHT);
                nameList.setPreferredSize(new Dimension(150, 20));
                picture.setPreferredSize(new Dimension(300, 400));

                add(nameList, "cell 1 1");
                add(picture, "grow, span");
                setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

            } catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
    }

    public Main(){
        super(new MigLayout());
        add(loading, "gapx 200, gapy 100, wrap");
        add(progressBar, "gapx 200, gapy 100");
        worker task = new worker();
        task.addPropertyChangeListener(
                evt -> {
                        if("progress".equals(evt.getPropertyName())){
                            progressBar.setValue((Integer)evt.getNewValue());
                            System.out.println(evt.getNewValue());
                        }
                }
        );
        task.execute();
    }

    public void actionPerformed(ActionEvent e){
        JComboBox<String> cb = (JComboBox<String>)e.getSource();
        updateLabel(cb.getSelectedIndex());
    }

    protected void updateLabel(int index){
        picture.setIcon(pictures[index]);
        picture.setToolTipText(names[index]);
    }

    protected static ImageIcon createImageIcon(String path){

        if (path != null){
            ImageIcon imgIcon = new ImageIcon(path);
            java.awt.Image img = imgIcon.getImage();
            java.awt.Image resized = img.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
            return new ImageIcon(resized);
        }
        else {
            System.out.println("no file found: ");
            return null;
        }
    }
    private static void createAndShowGUI() throws ExecutionException, InterruptedException {
        JFrame frame = new JFrame("Essential Riverdale Reference Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent contentPane = new Main();
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);

        frame.setSize(new Dimension(550, 500));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}