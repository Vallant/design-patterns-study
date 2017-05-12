/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.LoginController;
import javax.swing.JFrame;
import javax.swing.JPanel;
import view.interfaces.LoginView;
import view.interfaces.MainView;

/**
 *
 * @author stephan
 */
public class MainViewSwing implements MainView
{
    
    private final JFrame frame;
    private JPanel loginPanel;
    
    public MainViewSwing()
    {
        this.frame = new JFrame("Design Pattern Case Study");
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void showLoginView(LoginController controller)
    {
        LoginView login = new LoginViewSwing(controller, frame);
        login.SwitchToLogin();
    }
    
    
}
