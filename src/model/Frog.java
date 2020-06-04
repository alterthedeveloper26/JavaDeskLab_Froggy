/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class Frog {

    private Icon appearance = new ImageIcon("src\\imgs\\onmyoji.png"); //--change this to change appearance
    private JLabel identity;
    private int point;
    private double fallVelocity;
    public boolean isDead;

    public Frog() {
    }

    public Frog(JLabel identity, double fallVelocity) {
        this.identity = identity;
        this.point = 0;
        this.fallVelocity = fallVelocity;
        this.identity.setIcon(appearance);
        this.identity.setVisible(true);
        this.isDead = false;
    }

    public double getFallVelocity() {
        return fallVelocity;
    }

    public void setFallVelocity(double fallVelocity) {
        this.fallVelocity = fallVelocity;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public JLabel getIdentity() {
        return identity;
    }

    public void setIdentity(JLabel identity) {
        this.identity = identity;
    }
    
    public Frog createDecentFrog(JPanel playScreen){
        double fallVelocity = 0.5;
        int frogX = 20;
        int frogY = playScreen.getHeight() / 2;
        int frogW = 50;
        int frogH = 50;
        JLabel identity = new JLabel();
        identity.setBounds(frogX, frogY, frogW, frogH);
        
        return new Frog(identity, fallVelocity);
    }

    public void fall() {
        identity.setLocation(identity.getX(), (int) (identity.getY() + fallVelocity));
        fallVelocity = fallVelocity + 0.1;
    }

    public void checkPoint(JButton pipe) {
        if (this.getIdentity().getX() == pipe.getX() + pipe.getWidth()) {
            this.setPoint(this.getPoint() + 1);
        }
    }

    public void checkCollision(JPanel playScreen, Obstacle obstacles) {
        for (JButton pipe : obstacles.getPipesList()) {
            if (identity.getY() < 0 || identity.getY() >= playScreen.getHeight()) {
                this.isDead = true;
            }
            if (identity.getBounds().intersects(pipe.getBounds())) {
                this.isDead = true;
            }
        }

    }
}
