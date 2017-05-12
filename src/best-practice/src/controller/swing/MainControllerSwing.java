/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.MainController;
import model.interfaces.LoginModel;
import model.interfaces.MainModel;
import view.common.ViewManager;
import view.interfaces.MainView;

/**
 *
 * @author stephan
 */
public class MainControllerSwing implements MainController
{
    private MainView mainView;
    private MainModel mainModel;
    private LoginModel loginModel;
    
    @Override
    public void init(String frontend)
    {
        ViewManager.InitInstance(frontend);
        mainView = ViewManager.GetInstance();
    }

    @Override
    public void setModel(MainModel model)
    {
        mainModel = model;
    }

    @Override
    public void switchToLogin()
    {
        assert(mainModel != null);
        if(loginModel == null)
            loginModel = mainModel.getLoginModel();
        
        mainView.showLoginView(new LoginControllerSwing(loginModel));
    }

    @Override
    public void switchToMainView()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void switchToAdminView()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void switchToStatisticView()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
