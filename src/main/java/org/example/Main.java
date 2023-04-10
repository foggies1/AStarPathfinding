package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("A* Pathfinding");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.pack();
        window.setVisible(true);

    }
}