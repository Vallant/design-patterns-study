package view.swing.settings;

import controller.interfaces.SettingsController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.interfaces.SettingsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class SettingsViewSwing implements SettingsView {

    private SettingsController controller;
    private final JFrame frame;
    private final SettingsPanel pMain;

    public SettingsViewSwing(JFrame frame) {
        this.frame = frame;
        pMain = new SettingsPanel();

        setListener();

    }

    private void setListener() {
        pMain.btReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.resetClicked();
            }
        });
        pMain.btApply.addActionListener(new ActionListener() {
            @Override
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

    @Override
    public void setController(SettingsController controller) {
        this.controller = controller;
    }

    @Override
    public void hide() {
        frame.remove(pMain);
        frame.repaint();
        frame.invalidate();
        frame.pack();
    }

    @Override
    public void show() {
        frame.add(pMain, BorderLayout.CENTER);
        frame.repaint();
        frame.invalidate();
        frame.pack();
    }

    @Override
    public void showError(String error) {
        JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setData(String firstName, String lastName, String email) {
        pMain.tfFirst.setText(firstName);
        pMain.tfLast.setText(lastName);
        pMain.tfEmail.setText(email);
        pMain.tfNewPwAgain.setText("");
        pMain.tfNewPw.setText("");
        pMain.tfOldPw.setText("");
    }

    @Override
    public void updateSuccessful() {
        JOptionPane.showMessageDialog(frame, "Update Successful", "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
