package view.javafx;

import controller.interfaces.SideBarController;
import data.User;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.interfaces.SideBarView;

public class SideBarViewFX implements SideBarView
{
  private BorderPane mainPane;
  private Stage mainStage;

  private final Button btProjects;
  private final Button btPersonalStatistic;
  private final Button btProjectStatistic;
  private final Button btAdministration;
  private final Button btSettings;
  private final Button btLogout;

  private final GridPane pSideBar;
  private final FlowPane pFloatPanel;
  //private final JButton btHelp;

  private SideBarController controller;

  public SideBarViewFX()
  {

    pSideBar = new GridPane();
    btProjects = new Button("Manage Projects");
    btPersonalStatistic = new Button("Personal Statistic");
    btProjectStatistic = new Button("Project Statistic");
    btAdministration = new Button("Administration");
    btSettings = new Button("Settings");
    btLogout = new Button("Logout");

    pFloatPanel = new FlowPane();
    pSideBar.add(btProjects, 0, 0);
    pSideBar.add(btPersonalStatistic, 0, 1);
    pSideBar.add(btProjectStatistic, 0, 2);
    pSideBar.add(btSettings, 0, 3);
    pSideBar.add(btLogout, 0, 4);

    pFloatPanel.getChildren().add(pSideBar);
    setListeners();
    //pFloatPanel.setBorder(new EtchedBorder());
    //pSideBar.add(btAdministration);

  }

  private void setListeners()
  {
    btProjects.setOnAction(actionEvent -> controller.projectsClicked());
    btPersonalStatistic.setOnAction(actionEvent -> controller.personalStatisticClicked());
    btAdministration.setOnAction(actionEvent -> controller.administrationClicked());

    btSettings.setOnAction(actionEvent -> controller.settingsClicked());

    btProjectStatistic.setOnAction(actionEvent -> controller.projectStatisticClicked());
    btLogout.setOnAction(actionEvent -> controller.logoutClicked());
  }

  @Override
  public void setController(SideBarController controller)
  {
    this.controller = controller;
  }

  @Override
  public void show(User.ROLE role)
  {
    if(role == User.ROLE.ADMIN)
      pSideBar.add(btAdministration, 0,5);
    else
      pSideBar.getChildren().remove(btAdministration);

    mainPane.setLeft(pFloatPanel);
    mainStage.show();
  }

  @Override
  public void hide()
  {
    mainPane.setLeft(null);
  }

  public void setMainPane(BorderPane mainPane)
  {
    this.mainPane = mainPane;
  }

  public void setMainStage(Stage mainStage)
  {
    this.mainStage = mainStage;
  }
}
