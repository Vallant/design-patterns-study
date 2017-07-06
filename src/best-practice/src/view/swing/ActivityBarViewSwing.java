/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ActivityBarController;

import javax.swing.*;

import view.interfaces.ActivityBarView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.Timer;

/**
 *
 * @author stephan
 */
public class ActivityBarViewSwing implements ActivityBarView
{
    private final JFrame frame;
    private ActivityBarController controller;

    private final JPanel pMain;
    private final JComboBox cbProject;
    private final JComboBox cbPhase;
    private final JButton btStartStop;
    private final JLabel lbDuration;

    private Duration duration;
    private final TimerTask task;



    public ActivityBarViewSwing(JFrame frame)
    {

        this.frame = frame;

        duration = Duration.ZERO;


        this.pMain = new JPanel(new GridLayout(1,4, 5, 5));
        this.cbProject = new JComboBox();
        this.cbPhase = new JComboBox();
        this.btStartStop = new JButton("Start Activity");
        this.lbDuration = new JLabel();
        updateDuration();
        btStartStop.setEnabled(false);


        pMain.add(cbProject);
        pMain.add(cbPhase);
        pMain.add(btStartStop);
        pMain.add(lbDuration);


        task = new TimerTask() {
            @Override
            public void run() {
                duration.plusSeconds(1);
                updateDuration();
            }
        };



        btStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.StartStopClicked();
            }
        });
    }

    @Override
    public void setActivityBarController(ActivityBarController controller)
    {
        this.controller = controller;
    }

    @Override
    public void show() {
        frame.add(pMain, BorderLayout.NORTH);
        update();
    }

    @Override
    public void enableStartStop() {
        btStartStop.setEnabled(true);
    }

    @Override
    public void setProjectPhases(ArrayList<String> phases)
    {
        cbPhase.removeAllItems();
        for(String phase : phases)
            cbPhase.addItem(phase);
    }

    @Override
    public void setProjects(ArrayList<String> projects) {
        cbProject.removeAllItems();
        for(String project : projects)
            cbProject.addItem(project);
    }

    @Override
    public void startTimer() {
        task.run();
    }

    @Override
    public void stopTimer() {
        task.cancel();
    }

    @Override
    public void resetTimer() {
        duration = Duration.ZERO;
    }

    @Override
    public void toggleButtonText()
    {
        if(btStartStop.getText() == "Start")
            btStartStop.setText("Stop");
        else
            btStartStop.setText("Start");
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void removeAll()
    {
        frame.remove(pMain);
    }

    private void update()
    {
        frame.revalidate();
        frame.repaint();
    }

    private void updateDuration()
    {
        long seconds = duration.getSeconds();
        lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
    }
}
