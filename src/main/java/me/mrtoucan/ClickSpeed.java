package me.mrtoucan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.Timer;

/**
 * @Copyright 2016 MrToucan
 * Created by Anthony on 2/10/2017.
 */
public class ClickSpeed {

    private boolean started = false;
    private JFrame frame;
    private int WIDTH = 1350;
    private int HEIGHT = 1750;
    private AnimationPanel panel;
    private java.util.Timer timer;
    private long endTime;
    private long startTime;
    private Button startButton;
    private Label speed;
    private int clicks = 0;

    public ClickSpeed() {
        frame = new JFrame("ReactionTime");
        frame.setSize(WIDTH, HEIGHT);

        panel = new AnimationPanel();
        panel.addMouseListener(new clickListener());
        frame.getContentPane().add(BorderLayout.CENTER, panel);

        startButton = new Button("Start");
        startButton.setFont(new Font("serif", 1, 40));
        startButton.addActionListener(new buttonListener());
        frame.getContentPane().add(BorderLayout.SOUTH, startButton);

        speed = new Label("SPEED: 0 CLICKS/SECOND");
        speed.setFont(new Font("serif", 1, 40));
        frame.getContentPane().add(BorderLayout.EAST, speed);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ClickSpeed time = new ClickSpeed();
    }

    private void start() {
        if(!started) {
            started = true;
            startTime = System.currentTimeMillis();
            endTime = System.currentTimeMillis()+10000;
            startButton.setLabel("RUNNING");
            timer = new Timer();
            timer.schedule(new runnerTask(), 0, 1000);
        }
    }

    private void stop() {
        timer.cancel();
        recalculate();
        started = false;
        startButton.setLabel("Start");
        clicks = 0;
    }

    private void recalculate() {
        if(started) {
            if(clicks > 0) {
                long difference = (System.currentTimeMillis()-startTime)/1000;
                double speed = clicks/difference;
                this.speed.setText("SPEED: " + String.valueOf(speed) + " CLICKS/SECOND");
            }
        }
    }
    private class AnimationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.cyan);
            g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
            g.setColor(Color.yellow);
            if(started)
                g.drawString("CLICK", panel.getWidth()/2, panel.getHeight()/2);

        }
    }
    private class runnerTask extends TimerTask {
        public void run() {
            long difference = endTime - System.currentTimeMillis();
            if(difference > 0) {
                recalculate();
            } else {
                stop();
            }
        }
    }

    private class clickListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if(started)
                clicks++;
        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }
    private class buttonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            start();
        }
    }


}
