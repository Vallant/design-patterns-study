package view.javafx;

import controller.interfaces.SideBarController;
import data.User;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
  private static int BUTTON_WIDTH = 150;

  public SideBarViewFX()
  {
    pSideBar = new GridPane();
    pSideBar.setVgap(5);
    pSideBar.setHgap(5);
    pSideBar.setPadding(new Insets(5,5,5,5));

    btProjects = new Button("Manage Projects");
    btProjects.setPrefWidth(BUTTON_WIDTH);
    btPersonalStatistic = new Button("Personal Statistic");
    btPersonalStatistic.setPrefWidth(BUTTON_WIDTH);
    btProjectStatistic = new Button("Project Statistic");
    btProjectStatistic.setPrefWidth(BUTTON_WIDTH);
    btAdministration = new Button("Administration");
    btAdministration.setPrefWidth(BUTTON_WIDTH);
    btSettings = new Button("Settings");
    btSettings.setPrefWidth(BUTTON_WIDTH);
    btLogout = new Button("Logout");
    btLogout.setPrefWidth(BUTTON_WIDTH);

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

    GridPane.setHgrow(pSideBar, Priority.NEVER);
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

    mainPane.setLeft(pSideBar);
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
