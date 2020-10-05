package com.lebus.swingy.view;

import com.lebus.swingy.controller.Controller;
import com.lebus.swingy.model.PlayerCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GuiPages {

    private static JFrame window;
    private static JPanel mainWindow;
    JPanel startPage;
    JPanel createHeroPage;
    JPanel selectHeroPage;
    private static JPanel gamePage;
    private static CardLayout cardLayout;
    String jTextFieldValue = null;
    private static Controller controller;
    private static String value = null;
    private static String value2 = null;
    private static TextArea heroInfo = null;
    private static TextArea heroInformation = null;

    public GuiPages(Controller controller) {
        this.controller = controller;
        window = new JFrame("Swingy | lnzimand");
        mainWindow = new JPanel();
        startPage = getSPage();
        createHeroPage = getCHPPanel();
        cardLayout = new CardLayout();
        mainWindow.setLayout(cardLayout);
        mainWindow.add(startPage, "Start Page");
        mainWindow.add(createHeroPage, "Create Hero");
        cardLayout.show(mainWindow, "Start Page");
        window.add(mainWindow);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(600, 500));
        window.setLocationByPlatform(true);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    private JPanel getSPage() {
        JPanel jPanel1 = new JPanel();
        JButton jButton1 = new JButton();
        JButton jButton2 = new JButton();

        jPanel1.setBorder(BorderFactory.createTitledBorder("Start"));

        jButton1.setText("CREATE HERO");
        jButton1.addActionListener(event -> cardLayout.show(mainWindow, "Create Hero"));

        jButton2.setText("SELECT HERO");
        jButton2.addActionListener(evt -> {
            ArrayList<PlayerCharacter> heroes = controller.getHeroes();
            if (!heroes.isEmpty()){
                selectHeroPage = getSHPPanel();
                mainWindow.add(selectHeroPage, "Select Hero");
                cardLayout.show(mainWindow, "Select Hero");
            } else info("No heroes exist, create hero");
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(207, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(143, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(window.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        return jPanel1;
    }

    private JPanel getCHPPanel() {
        JPanel jPanel1 = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JTextField jTextField1 = new JTextField();
        JComboBox jComboBox1 = new JComboBox<>();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTextArea jTextArea1 = new JTextArea();
        JButton jButton1 = new JButton();

        jPanel1.setBorder(BorderFactory.createTitledBorder("Create Hero"));

        jLabel1.setText("Hero Name");
        jLabel2.setText("Hero Class");

        if (value == null) value = "Dimachaeri";
        if (heroInfo == null) heroInfo = HeroInformation.getDimachaeriInfo();
        if (jTextFieldValue != null) jTextField1.setText(jTextFieldValue);
        jTextArea1.setText(heroInfo.getText());
        jTextArea1.setEditable(false);
        jComboBox1.setSelectedItem("Dimachaeri");
        jComboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Dimachaeri", "Gaul", "Retiarius", "Thraex", "Hoplomachus", "Murmillo"}));
        jComboBox1.addActionListener(evt -> {
            Object selectedItem = jComboBox1.getSelectedItem();
            if ("Dimachaeri".equals(selectedItem)) {
                heroInfo = HeroInformation.getDimachaeriInfo();
            } else if ("Gaul".equals(selectedItem)) {
                heroInfo = HeroInformation.getGaulInfo();
                value = "Gaul";
            } else if ("Murmillo".equals(selectedItem)) {
                heroInfo = HeroInformation.getMurmilloInfo();
                value = "Murmillo";
            } else if ("Hoplomachus".equals(selectedItem)) {
                heroInfo = HeroInformation.getHoplomachusInfo();
                value = "Hoplomachus";
            } else if ("Retiarius".equals(selectedItem)) {
                heroInfo = HeroInformation.getRetiariusInfo();
                value = "Retiarius";
            } else {
                heroInfo = HeroInformation.getThraexInfo();
                value = "Thraex";
            }
            jTextArea1.setText(heroInfo.getText());
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Submit");
        jButton1.addActionListener(evt -> {
            String error = controller.createHero(jTextField1.getText(), value, heroInfo.getText());
            if (error == null) {
                gamePage = getGPPanel();
                mainWindow.add(gamePage, "Game Page");
                cardLayout.show(mainWindow, "Game Page");
            } else JOptionPane.showMessageDialog(window, error, "Error", JOptionPane.ERROR_MESSAGE);
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addGap(145, 145, 145)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                                                        .addComponent(jComboBox1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField1)
                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(43, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(window.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        return jPanel1;
    }

    private static JPanel getSHPPanel() {
        JPanel jPanel1 = new JPanel();
        JComboBox jComboBox1 = new JComboBox<>();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTextArea jTextArea1 = new JTextArea();
        JButton jButton1 = new JButton();
        ArrayList<PlayerCharacter> heroes = controller.getHeroes();

        if (value2 == null) value2 = controller.getHeroes().get(0).getName();
        if (heroInformation == null) heroInformation = HeroInformation.getTextHeroInfo(heroes.get(0));

        jPanel1.setBorder(BorderFactory.createTitledBorder("Select Hero"));

        jComboBox1.setSelectedItem(value2);
        ArrayList<String> strings = new ArrayList<>();
        for (PlayerCharacter player: heroes
             ) {
            strings.add(player.getName());
        }
        jComboBox1.setModel(new DefaultComboBoxModel<>(strings.toArray()));
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                value2 = jComboBox1.getSelectedItem().toString();
                for (PlayerCharacter player: heroes
                     ) {
                    if (player.getName().equals(value2)) {
                        jTextArea1.setText(HeroInformation.getTextHeroInfo(player).getText());
                    }
                };
            }
        });

        jTextArea1.setText(heroInformation.getText());
        jTextArea1.setEditable(false);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Submit");
        jButton1.addActionListener(evt -> {
            controller.selectedHero(value2, heroes);
            gamePage = getGPPanel();
            mainWindow.add(gamePage, "Game Page");
            cardLayout.show(mainWindow, "Game Page");
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                                .addComponent(jComboBox1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(72, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(window.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        return jPanel1;
    }

    private static JPanel getGPPanel() {
        JPanel jPanel1 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTextArea jTextArea1 = new JTextArea();
        JScrollPane jScrollPane2 = new JScrollPane();
        JTextArea jTextArea2 = new JTextArea();
        JButton jButton1 = new JButton();
        JButton jButton2 = new JButton();
        JButton jButton3 = new JButton();
        JButton jButton4 = new JButton();

        jPanel1.setBorder(BorderFactory.createTitledBorder("Game"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        if (controller.getPlayer() != null) {
            PlayerCharacter player = controller.getPlayer();
            jTextArea1.append(getHeroInfo(player));
            jTextArea2.append(drawMap(player));
        }

        jButton1.setText("UP");
        jButton2.setText("LEFT");
        jButton3.setText("RIGHT");
        jButton4.setText("DOWN");
        jButton1.addActionListener(evt -> {
            controller.movement("UP");
            PlayerCharacter player = controller.getPlayer();
            jTextArea1.setText(getHeroInfo(player));
            jTextArea2.setText(drawMap(player));
        });
        jButton2.addActionListener(evt -> {
            controller.movement("LEFT");
            PlayerCharacter player = controller.getPlayer();
            jTextArea1.setText(getHeroInfo(player));
            jTextArea2.setText(drawMap(player));
        });
        jButton3.addActionListener(evt -> {
            controller.movement("RIGHT");
            PlayerCharacter player = controller.getPlayer();
            jTextArea1.setText(getHeroInfo(player));
            jTextArea2.setText(drawMap(player));
        });
        jButton4.addActionListener(evt -> {
            controller.movement("DOWN");
            PlayerCharacter player = controller.getPlayer();
            jTextArea1.setText(getHeroInfo(player));
            jTextArea2.setText(drawMap(player));
        });

        jTextArea1.setEditable(false);
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 20, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton1)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jButton4))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jButton3))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(27, 27, 27)
                                                                .addComponent(jButton2)))
                                                .addGap(0, 17, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(window.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        return jPanel1;
    }

    private static String drawMap(PlayerCharacter player) {
        TextArea textArea = new TextArea();
        for (int index = 0; index < player.getMap().getSize(); index++) {
            for (int index2 = 0; index2 < player.getMap().getSize(); index2++) {
                if (index == ((player.getLevel()-1)*5+10-(player.getLevel()%2)) / 2 && index2 == ((player.getLevel()-1)*5+10-(player.getLevel()%2)) / 2) {
                    if (player.getCoordinates().getX() == index2 && player.getCoordinates().getY() == index)
                        textArea.append("O");
                    else textArea.append("C");
                }
                else if (index == player.getCoordinates().getY() && index2 == player.getCoordinates().getX()) {
                    textArea.append("O");
                    if (player.getMap().equals(true))
                        textArea.append(" X");
                } else {
                    textArea.append("*");
                }
                textArea.append("   ");
            }
            textArea.append("\n");
        }
        return textArea.getText();
    }

    public static boolean confirmationGui(String message, String title) {
        int answer = JOptionPane.showConfirmDialog(window, message, title, JOptionPane.YES_NO_OPTION);
        if (answer == 0)
            return true;
        else return false;
    }

    public static void gameOver() {
        window.setVisible(false);
        JOptionPane.showMessageDialog(window, "YOU LOST", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
        controller.exitProgram();
    }

    public static void gameOver(String string) {
        window.setVisible(false);
        JOptionPane.showMessageDialog(window, string, "GAME OVER", JOptionPane.PLAIN_MESSAGE);
        controller.exitProgram();
    }

    public static void info(String info) {
        JOptionPane.showMessageDialog(window, info);
    }

    public static void showStartPage() {
        cardLayout.show(mainWindow, "Start Page");
    }

    private static String getHeroInfo(PlayerCharacter player) {
        String info = "Name\t: " + player.getName() + "\nHealth\t: " + player.getHealth() + "\nAttack\t: " +
                player.getAttack() + "\nDefense\t: " + player.getDefense() + "\nHit Points\t: " + player.getHitPoints()
                + "\nExperience\t: " + player.getExperience() + "\n";
        if (player.getHelmet() != null) info += "HELMET\t: \n    " + player.getHelmet().getName() + " +" + player.getHelmet().getHitPoints() + "\n";
        if (player.getWeapon() != null) info += "WEAPON\t: \n    " + player.getWeapon().getName() + " +" + player.getWeapon().getAttack() + "\n";
        if (player.getArmor() != null) info += "ARMOR\t: \n    " + player.getArmor().getName() + " +" + player.getArmor().getDefense() + "\n";
        return info;
    }
}
