package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Getter
@Setter
public class Node extends JButton implements ActionListener, KeyListener {

    private GamePanel gamePanel;
    private Node parent;
    private int col;
    private int row;
    private boolean start;
    private boolean end;
    private boolean wall;
    private boolean open;
    private boolean checked;
    private int gCost; // Distance between current node and start node
    private int hCost; // Distance between current node and end node
    private int fCost; // gCost + hCost

    public Node(GamePanel gamePanel, int col, int row){
        this.gamePanel = gamePanel;
        this.col = col;
        this.row = row;
        this.setOpaque(true);
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        this.addActionListener(this);
        this.addKeyListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        setWall();
    }

    public void setStart() {
        this.start = true;
        this.setBackground(Color.GREEN);
        this.setText("Start");
    }

    public void setEnd(){
        this.end = true;
        this.setBackground(Color.RED);
        this.setText("End");
    }

    public void setWall() {
        this.wall = true;
        this.setForeground(Color.black);
        this.setBackground(Color.black);
        this.setText("");
    }

    public void setOpen(){
        this.open = true;
    }

    public void setChecked(){
        if(!isEnd() && !isStart()){
            setBackground(Color.pink);
        }
        this.checked = true;
    }

    public void unCheck(){
        this.start = true;
        setBackground(Color.WHITE);
        this.checked = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.search();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
