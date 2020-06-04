/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.MainScreen;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Obstacle;
import model.Frog;
import model.Memory;

/**
 *
 * @author admin
 */
public class PlaySController implements Runnable, KeyListener {

    private MainScreen mainScreen;
    private JLabel lbPoint;
    private JPanel playScreen;
    private JButton btnExit, btnSave, btnPause;
    
    private Frog frog;
    private Obstacle obstacle;
    
    private Boolean isPaused, isSaved;
    private Memory rom;
    Thread t;

    public PlaySController() {
        initComponents();
        createPattern();
        mainScreen.setVisible(true);
        
        t = new Thread(this);
        t.start();
    }
    
    public void initComponents(){
        rom = new Memory();
        isPaused = true;
        isSaved = false;
        mainScreen = new MainScreen();
        
        lbPoint = mainScreen.getLbPoint();

        playScreen = mainScreen.getGamePanel();
        playScreen.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        
        mainScreen.addKeyListener(this);
        
        mainScreen.getBtnExit().setFocusable(false);
        mainScreen.getBtnPause().setFocusable(false);
        mainScreen.getBtnSave().setFocusable(false);
        
        mainScreen.getBtnPause().setEnabled(false);
        mainScreen.getBtnSave().setEnabled(false);
        
        btnExit = mainScreen.getBtnExit();
        btnPause = mainScreen.getBtnPause();
        btnSave = mainScreen.getBtnSave();
        
        btnExit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnPause.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.exit(0);
    }

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        pause();
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        rom.save(obstacle, frog);
        isSaved = true;
    }

    public void pause() {
        if (!isPaused) {
            isPaused = true;
            btnPause.setText("Continue");
        } else {
            isPaused = false;
            btnPause.setText("Pause");
        }
    }

    public void reset() {
        playScreen.removeAll();
        createPattern();
        playScreen.revalidate();
        playScreen.repaint();
        isPaused = true;
        isSaved = false;
        btnPause.setEnabled(false);
        btnSave.setEnabled(false);
        t = new Thread(this);
        t.start();
    }

    public void load() {
        playScreen.removeAll();
        frog = rom.getFrog();
        obstacle = rom.getPipes();
        for (JButton pipe : obstacle.getPipesList()) {
            playScreen.add(pipe);
        }
        playScreen.add(frog.getIdentity());
        playScreen.revalidate();
        playScreen.repaint();
        isPaused = true;
        isSaved = false;
        btnPause.setEnabled(false);
        btnSave.setEnabled(false);
        
        t = new Thread(this);
        t.start();
    }

    public void showMessageDialog() {
        int point = frog.getPoint();
        String reward = "No reward!";
        if (point >= 10 && point < 20) {
            reward = "Brozen archived!";
        }
        if (point >= 20) {
            reward = "Silver archived!";
        }
        if (point >= 30) {
            reward = "Gold archived!";
        }

        if (isSaved) {
            Object[] messages = {"Continue", "New Game", "Quit"};
            int response = JOptionPane.showOptionDialog(null, reward, "Notice!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messages, null);
            if (response == 0) {
                load();
            } else if (response == 1) {
                reset();
            } else {
                System.exit(0);
            }
        } else {
            Object[] messages = {"New Game", "Quit"};
            int response = JOptionPane.showOptionDialog(null, reward, "Notice!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messages, null);
            if (response == 0) {
                reset();
            } else {
                System.exit(0);
            }
        }
    }

    public void createPattern() {
        Random r = new Random();
        //Create frog
        frog = new Frog();
        frog = frog.createDecentFrog(playScreen);
        //Create pipes    
        obstacle = new Obstacle(frog, playScreen);
        obstacle.addPipe(5, frog, playScreen);
        //Add to this panel
        playScreen.add(frog.getIdentity());
        for (JButton pipe : obstacle.getPipesList()) {
            playScreen.add(pipe);
        }
    }

    public void movePatterns() {
        ArrayList<JButton> pipes = obstacle.getPipesList();
        for (int i = 0; i < pipes.size(); i += 2) {
            //move pipe
            if (pipes.get(i).getX() + pipes.get(i).getWidth() <= 0) {
                playScreen.remove(pipes.get(i));
                playScreen.remove(pipes.get(i + 1));
                pipes.remove(pipes.get(i));
                pipes.remove(pipes.get(i));
                obstacle.addPipe(1, frog, playScreen);
            }
        }

        for(JButton pipe: obstacle.getPipesList()){
            pipe.setLocation(pipe.getX() - 1, pipe.getY());
            frog.checkPoint(pipe);
            frog.checkCollision(playScreen, obstacle);
        }
        
        frog.fall();
//        System.out.println("--------------------------");
    }

    public void updatePoint(){
        lbPoint.setText("Point: " + frog.getPoint());
    }
    
    public void enableBtns(){
        btnPause.setEnabled(true);
        btnSave.setEnabled(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (isPaused) {
                    System.out.println("in--loop");
                }
                
                enableBtns();
                movePatterns();
                updatePoint();
                
                if (frog.isDead) {
                    showMessageDialog();
                    return;
                }
                
                Thread.sleep(6);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlaySController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            frog.setFallVelocity(frog.getFallVelocity() - 8);
            if (isPaused == true) {
                isPaused = false;
                frog.setFallVelocity(frog.getFallVelocity() + 6);
            }
//            System.out.println("gameBef: " + gameBeg);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
