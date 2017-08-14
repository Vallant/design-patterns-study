package view.javafx;

import controller.interfaces.*;
import data.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.interfaces.MainView;

import java.io.IOException;

public class MainViewFX extends Application implements MainView
{
  private static MainViewFX theInstance;
  private Stage stage;


  public static MainViewFX getInstance()
  {
    if(theInstance == null)
      theInstance = new MainViewFX();
    return theInstance;
  }

  public MainViewFX()
  {

  }

  public Stage getStage()
  {
    return stage;
  }

  public void launchThis()
  {
    launch();

  }

  @Override
  public void start(Stage stage) throws Exception
  {
    this.stage = stage;
    theInstance = this;
  }

  @Override
  public void setMainController(MainController controller)
  {

  }

  @Override
  public void showLoginView()
  {
    Platform.runLater(new Runnable()
    {
      @Override
      public void run()
      {
        Parent root = new Pane();
        theInstance.stage.setTitle("Hello World 2");
        theInstance.stage.setScene(new Scene(root, 300, 275));
        theInstance.stage.show();
      }
    });

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
