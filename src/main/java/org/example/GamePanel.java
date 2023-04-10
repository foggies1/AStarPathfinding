package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class GamePanel extends JPanel {

    private final int maxCol = 10;
    private final int maxRow = 10;
    private final int nodeSize = 70;
    private final int screenWidth = maxCol * nodeSize;
    private final int screenHeight = maxRow * nodeSize;
    private Node current, start, end;
    private boolean endReached;
    private Node[][] nodes = new Node[maxCol][maxRow];
    private ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> checkedList = new ArrayList<>();

    public GamePanel() {
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        this.setVisible(true);

        initialiseNodes();

        setStart(1, 5);

        setWall(4, 2);
        setWall(4, 3);
        setWall(5, 3);
        setWall(6, 3);
        setWall(7, 3);
        setWall(8, 3);

        setEnd(8, 2);

        calculateAllCost();
        search();

    }


    public void search(){


        while(!endReached){

            int col = this.current.getCol();
            int row = this.current.getRow();

            this.current.setChecked();
            this.openList.remove(this.current); // The current node is no longer open
            this.checkedList.add(this.current); // The current node has been checked

            // Above
            if(row - 1 >= 0)
                openNode(nodes[col][row - 1]);
            // Below
            if(row + 1 < maxCol)
                openNode(nodes[col][row + 1]);
            // Right
            if(col + 1 < maxCol)
                openNode(nodes[col + 1][row]);
            // Left
            if(col - 1 >= 0)
                openNode(nodes[col - 1][row]);

            int bestIndex = 0;
            int bestFCost = 999;

            for(int index = 0; index < this.openList.size(); index++){
                Node node = this.openList.get(index);
                if(node.getFCost() < bestFCost){
                    bestIndex = index;
                    bestFCost = node.getFCost();
                } else if(node.getFCost() == bestFCost){
                    if(node.getGCost() < this.openList.get(bestIndex).getGCost()){
                        bestIndex = index;
                    }
                }
            }

            current = this.openList.get(bestIndex);
            if(current == end){
                endReached = true;
            }

        }

        Node current = end;
        while(current != start){
            current = current.getParent();
            if(current != start){
                current.setBackground(Color.MAGENTA);
            }
        }

    }

    public void calculateAllCost(){
        for(int col = 0; col < this.maxCol; col++){
            for(int row = 0; row < this.maxRow; row++){
                calculateCost(nodes[col][row]);
            }
        }
    }

    public void calculateCost(Node node) {

        int xDistance = Math.abs(node.getCol() - this.start.getCol());
        int yDistance = Math.abs(node.getRow() - this.start.getRow());

        node.setGCost(xDistance + yDistance);

        xDistance = Math.abs(node.getCol() - this.end.getCol());
        yDistance = Math.abs(node.getRow() - this.end.getRow());

        node.setHCost(xDistance + yDistance);
        node.setFCost(node.getGCost() + node.getHCost());

        if (node.isStart() || node.isWall() || node.isEnd()) return;

        node.setText(
                "<html>" +
                        "F: " + node.getFCost() +
                        "<br>" +
                        "G: " + node.getGCost() +
                        "</html>"
        );

    }

    public void initialiseNodes() {
        for (int col = 0; col < this.maxCol; col++) {
            for (int row = 0; row < this.maxRow; row++) {
                Node node = new Node(this, col, row);
                nodes[col][row] = node;
                this.add(node);
            }
        }
    }

    public void openNode(Node node){
        if(!node.isOpen() && !node.isChecked() && !node.isWall()) {
            // If the node is not open yet, and it has not been checked, and it's not a wall, open it.
            node.setOpen();
            node.setParent(this.current);
            this.openList.add(node);
        }
    }

    public void setStart(int col, int row){
        nodes[col][row].setStart();
        this.start = nodes[col][row];
        this.current = nodes[col][row];
    }

    public void setEnd(int col, int row){
        nodes[col][row].setEnd();
        this.end = nodes[col][row];
    }

    public void setWall(int col, int row){
        nodes[col][row].setWall();
    }


}
