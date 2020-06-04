/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author admin
 */
public class Memory {
    private Frog frog;
    private Obstacle pipes;

    public Memory() {
    }

    public Memory(Frog frog, Obstacle pipes) {
        this.frog = frog;
        this.pipes = pipes;
    }

    public Frog getFrog() {
        return frog;
    }

    public void setFrog(Frog frog) {
        this.frog = frog;
    }

    public Obstacle getPipes() {
        return pipes;
    }

    public void setPipes(Obstacle pipes) {
        this.pipes = pipes;
    }
    
    public void save(Obstacle obstacle, Frog frog) {
        JLabel savedIdentity = new JLabel();
        savedIdentity.setBounds(frog.getIdentity().getX(), frog.getIdentity().getY(), frog.getIdentity().getWidth(), frog.getIdentity().getHeight());
        double fallVelocity = frog.getFallVelocity();
//        System.out.println("fall vel" + fallVelocity);
        Frog savedF = new Frog(savedIdentity, fallVelocity);
        int gap = obstacle.getVerticalGap();
        int space = obstacle.getHorizontalGap();
        ArrayList<JButton> pipes = new ArrayList<>();
        for(JButton pipe: obstacle.getPipesList()){
            JButton savedPipe = new JButton();
            savedPipe.setBounds(pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight());
            pipes.add(savedPipe);
        }
        Obstacle savedO = new Obstacle(obstacle.getVerticalGap(), obstacle.getHorizontalGap(), pipes);
//        Memory m = new Memory(frog, obstacle);

        this.setFrog(savedF);
        this.setPipes(savedO);
    }
}
