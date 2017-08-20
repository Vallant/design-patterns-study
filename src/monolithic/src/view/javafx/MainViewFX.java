package view.javafx;

import controller.javafx.*;
import data.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.javafx.activitybar.ActivityBarViewFX;
import view.javafx.login.LoginViewFX;
import view.javafx.personalstatistic.PersonalStatisticViewFX;
import view.javafx.project.ProjectViewFX;
import view.javafx.projectstatistic.ProjectStatisticViewFX;
import view.javafx.settings.SettingsViewFX;

public class MainViewFX extends Application
{
  private static MainViewFX       theInstance;
  private        Stage            mainStage;
  private        BorderPane       mainPane;
  private        MainControllerFX controller;

  private final LoginViewFX             login;
  private final PersonalStatisticViewFX personalStatistic;
  private final ProjectStatisticViewFX  projectStatistic;
  private final SettingsViewFX          settings;
  private final ProjectViewFX           project;
  private final ActivityBarViewFX       activityBar;
  private final SideBarViewFX sideBar;


  public static MainViewFX getInstance()
  {
    if(theInstance == null)
    {
      new JFXPanel();
      theInstance = new MainViewFX();
    }

    return theInstance;
  }

  public MainViewFX()
  {
    login = new LoginViewFX();
    personalStatistic = new PersonalStatisticViewFX();
    projectStatistic = new ProjectStatisticViewFX();
    settings = new SettingsViewFX();
    activityBar = new ActivityBarViewFX();
    project = new ProjectViewFX();
    sideBar = new SideBarViewFX();
  }

  public Stage getMainStage()
  {
    return mainStage;
  }

  public void launchThis()
  {
    launch();

  }

  
  public void start(Stage mainStage) throws Exception
  {
    this.mainStage = mainStage;

    theInstance = this;
    mainStage.setTitle("Design Pattern Case Study");
    theInstance.mainPane = new BorderPane();

    theInstance.login.setMainPane(mainPane);
    theInstance.login.setMainStage(mainStage);

    theInstance.personalStatistic.setMainPane(mainPane);
    theInstance.personalStatistic.setMainStage(mainStage);

    theInstance.activityBar.setMainPane(mainPane);
    theInstance.activityBar.setMainStage(mainStage);

    theInstance.projectStatistic.setMainPane(mainPane);
    theInstance.projectStatistic.setMainStage(mainStage);

    theInstance.settings.setMainPane(mainPane);
    theInstance.settings.setMainStage(mainStage);

    theInstance.sideBar.setMainPane(mainPane);
    theInstance.sideBar.setMainStage(mainStage);

    theInstance.project.setMainPane(mainPane);
    theInstance.project.setMainStage(mainStage);


    theInstance.mainStage.setScene(new Scene(mainPane));
  }

  
  public void setMainController(MainControllerFX controller)
  {
    theInstance.controller = controller;
  }

  
  public void showLoginView()
  {
    Platform.runLater(new Runnable()
    {
      
      public void run()
      {
        theInstance.sideBar.hide();
        theInstance.activityBar.hide();
        theInstance.login.switchToLogin();
        theInstance.mainStage.show();

      }
    });

  }

  
  public void showProjectView()
  {
    theInstance.login.removeAllComponents();
    theInstance.personalStatistic.RemoveAllComponents();
    theInstance.projectStatistic.hide();
    theInstance.settings.hide();
    theInstance.project.showOverview();

  }

  
  public void pairLogin(LoginControllerFX controller)
  {
    controller.setView(theInstance.login);
    theInstance.login.setController(controller);
  }

  
  public void pairProject(ProjectControllerFX controller)
  {
    controller.setView(theInstance.project);
    theInstance.project.setController(controller);
  }

  
  public void pairActivityBar(ActivityBarControllerFX controller)
  {
    controller.setView(theInstance.activityBar);
    theInstance.activityBar.setController(controller);
  }

  
  public void showError(String message)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, message);
    alert.showAndWait();
  }

  
  public void showActivityBar()
  {
    theInstance.activityBar.show();
  }

  
  public void pairSideBar(SideBarControllerFX controller)
  {
    controller.setView(theInstance.sideBar);
    theInstance.sideBar.setController(controller);
  }

  
  public void showSideBar(User.ROLE role)
  {
    theInstance.sideBar.show(role);
  }

  
  public void hideCenterContent()
  {
    theInstance.mainPane.setCenter(null);
    theInstance.mainStage.show();
  }

  
  public void showAdminView()
  {

  }

  
  public void showPersonalStatisticView()
  {
    theInstance.login.removeAllComponents();
    theInstance.personalStatistic.showProjectView();
    theInstance.projectStatistic.hide();
    theInstance.settings.hide();
    theInstance.project.hide();
    theInstance.mainStage.show();
  }

  
  public void showProjectStatisticView()
  {
    theInstance.login.removeAllComponents();
    theInstance.personalStatistic.RemoveAllComponents();
    theInstance.projectStatistic.showProjectView();
    theInstance.settings.hide();
    theInstance.project.hide();
    theInstance.mainStage.show();
  }

  
  public void showSettingsView()
  {
    theInstance.settings.show();
    theInstance.mainStage.show();
  }

  
  public void pairPersonalStatistic(PersonalStatisticControllerFX statistics)
  {
    theInstance.personalStatistic.setController(statistics);
    statistics.setView(theInstance.personalStatistic);
  }

  
  public void pairProjectStatistic(ProjectStatisticControllerFX projectStatistic)
  {
    theInstance.projectStatistic.setController(projectStatistic);
    projectStatistic.setView(theInstance.projectStatistic);
  }

  
  public void hideAll()
  {

  }

  
  public void pairSettings(SettingsControllerFX controller)
  {
    theInstance.settings.setController(controller);
    controller.setView(theInstance.settings);
  }
}
