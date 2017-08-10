package view.javafx;

import controller.interfaces.*;
import data.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.interfaces.MainView;

import java.io.IOException;

public class MainViewFX extends Application implements MainView
{
  private Stage stage;


  public MainViewFX()
  {

  }
  public static void launchThis()
  {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    this.stage = stage;
    Parent root = null;
    root = FXMLLoader.load(getClass().getResource("login.fxml"));
    stage.setTitle("Hello World");
    stage.setScene(new Scene(root, 300, 275));
    stage.show();
  }

  @Override
  public void setMainController(MainController controller)
  {

  }

  @Override
  public void showLoginView()
  {
    this.stage = stage;
    Parent root = null;
    try
    {
      root = FXMLLoader.load(getClass().getResource("login.fxml"));
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    stage.setTitle("Hello World 2");
    stage.setScene(new Scene(root, 300, 275));
    stage.show();
  }

  @Override
  public void showProjectView()
  {

  }

  @Override
  public void pairLogin(LoginController controller)
  {

  }

  @Override
  public void pairProject(ProjectController controller)
  {

  }

  @Override
  public void pairActivityBar(ActivityBarController controller)
  {

  }

  @Override
  public void showError(String message)
  {

  }

  @Override
  public void showActivityBar()
  {

  }

  @Override
  public void pairSideBar(SideBarController sideBar)
  {

  }

  @Override
  public void showSideBar(User.ROLE role)
  {

  }

  @Override
  public void hideCenterContent()
  {

  }

  @Override
  public void showAdminView()
  {

  }

  @Override
  public void showPersonalStatisticView()
  {

  }

  @Override
  public void showProjectStatisticView()
  {

  }

  @Override
  public void showSettingsView()
  {

  }

  @Override
  public void pairPersonalStatistic(PersonalStatisticController statistics)
  {

  }

  @Override
  public void pairProjectStatistic(ProjectStatisticController projectStatistic)
  {

  }

  @Override
  public void hideAll()
  {

  }

  @Override
  public void pairSettings(SettingsController settings)
  {

  }
}
