/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class Obstacle {

    private int verticalGap, horizontalGap, firstPX;
    private ArrayList<JButton> pipesList;

    public Obstacle() {
    }
  
    public Obstacle(Frog frog, JPanel playScreen) {
        Random r = new Random();
        int frogH = frog.getIdentity().getHeight();
        this.verticalGap = r.nextInt(frogH) + frogH * 4;
        this.horizontalGap = playScreen.getWidth() / 5;
        this.firstPX = playScreen.getWidth()/2;
        pipesList = new ArrayList<>();
    }

    public Obstacle(int verticalGap, int horizontalGap, ArrayList<JButton> pipesList) {
        this.verticalGap = verticalGap;
        this.horizontalGap = horizontalGap;
        this.pipesList = pipesList;
    } 

    public int getVerticalGap() {
        return verticalGap;
    }

    public void setVerticalGap(int verticalGap) {
        this.verticalGap = verticalGap;
    }

    public int getHorizontalGap() {
        return horizontalGap;
    }

    public void setHorizontalGap(int horizontalGap) {
        this.horizontalGap = horizontalGap;
    }

    public int getFirstPX() {
        return firstPX;
    }

    public void setFirstPX(int firstPX) {
        this.firstPX = firstPX;
    }

    public ArrayList<JButton> getPipesList() {
        return pipesList;
    }

    public void setPipesList(ArrayList<JButton> pipesList) {
        this.pipesList = pipesList;
    }

    //To use this function properly you should set the obstacle's firstPY
    public void addPipe(int pairNum, Frog frog, JPanel playScreen) {
        Random r = new Random();
        //Affected attributes
        int frogHeight = frog.getIdentity().getHeight();
        int motherW = playScreen.getWidth();
        int motherH = playScreen.getHeight();
        //Pipe attributes
        int pipeW = motherW / 12; //--change this to alter pipe width
        int upPipeY = 0; //--up pipe always stick to the ceiling
//        //create gap due to frog height
//        verticalGap = r.nextInt(frogHeight*4) + frogHeight;
//        horizontalGap = width*3;
        for (int i = 0; i < pairNum; i++) {
            JButton upPipe = new JButton();
            JButton downPipe = new JButton();
            //Up pipe height will be used to determin both up, down pipe height
            int upPipeH = r.nextInt(motherH / 2) + 30;

            //Check if the list is empty then the first pipe x is 0
            if (pipesList.isEmpty()) {
                upPipe.setBounds(firstPX, upPipeY, pipeW, upPipeH);
            } else {
                JButton prePipe = pipesList.get(pipesList.size() - 1);
                int newX = prePipe.getX() + prePipe.getWidth() + horizontalGap;
                upPipe.setBounds(newX, upPipeY, pipeW, upPipeH);
            }
            //Create down pipe
            int downPipeH = motherH - upPipeH - verticalGap;
            int downPipeY = upPipeH + verticalGap;
            downPipe.setBounds(upPipe.getX(), downPipeY, pipeW, downPipeH);
            upPipe.setFocusable(false);
            downPipe.setFocusable(false);
            pipesList.add(upPipe);
            pipesList.add(downPipe);
            //
            playScreen.add(upPipe);
            playScreen.add(downPipe);
        }
    }

}
