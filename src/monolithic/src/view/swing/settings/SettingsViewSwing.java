package view.swing.settings;

import controller.swing.SettingsControllerSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsViewSwing {

    private SettingsControllerSwing controller;
    private final JFrame frame;
    private final SettingsPanel pMain;

    public SettingsViewSwing(JFrame frame) {
        this.frame = frame;
        pMain = new SettingsPanel();

        setListener();

    }

    private void setListener() {
        pMain.btReset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                controller.resetClicked();
            }
        });
        pMain.btApply.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                controller.applyClicked(
                        pMain.tfFirst.getText(),
                        pMain.tfLast.getText(),
                        pMain.tfEmail.getText(),
                        pMain.tfOldPw.getPassword(),
                        pMain.tfNewPw.getPassword(),
                        pMain.tfNewPwAgain.getPassword()
                        );
            }
        });
    }


    public void setController(SettingsControllerSwing controller) {
        this.controller = controller;
    }


    public void hide() {
        frame.remove(pMain);
        frame.repaint();
        frame.invalidate();
        frame.pack();
    }


    public void show() {
        frame.add(pMain, BorderLayout.CENTER);
        frame.repaint();
        frame.invalidate();
        frame.pack();
    }


    public void showError(String error) {
        JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void setData(String firstName, String lastName, String email) {
        pMain.tfFirst.setText(firstName);
        pMain.tfLast.setText(lastName);
        pMain.tfEmail.setText(email);
        pMain.tfNewPwAgain.setText("");
        pMain.tfNewPw.setText("");
        pMain.tfOldPw.setText("");
    }


    public void updateSuccessful() {
        JOptionPane.showMessageDialog(frame, "Update Successful", "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
