package view.javafx.activitybar;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


class ActivityBarPane extends FlowPane
{
  final ComboBox<String> cbProject;
  final ComboBox<String> cbPhase;
  final Button           btStart;
  final Button           btStop;
  final Label            lbDuration;

  public ActivityBarPane()
  {
    setHgap(5);
    setPadding(new Insets(5, 5, 5, 5));

    GridPane pMain = new GridPane();
    pMain.setHgap(5);
    pMain.setVgap(5);
    pMain.setPadding(new Insets(5, 5, 5, 5));

    //FlowPane pFlow1 = new FlowPane();
    cbProject = new ComboBox<>();
    cbProject.setPrefWidth(100);

    Label lbProject = new Label("Project:");

    //FlowPane pFlow2 = new FlowPane();
    cbPhase = new ComboBox<>();
    cbPhase.setPrefWidth(100);
    Label lbPhase = new Label("Phase:");

    btStart = new Button("Start Activity");
    btStop = new Button("Stop Activity");
    lbDuration = new Label();
    lbDuration.setPrefWidth(100);
    btStart.setDisable(true);
    btStop.setDisable(true);


    //pFlow1.getChildren().add(lbProject);
    //pFlow1.getChildren().add(cbProject);
    pMain.add(lbProject, 0, 0);
    pMain.add(cbProject, 1, 0);
    pMain.add(lbPhase, 2, 0);
    pMain.add(cbPhase, 3, 0);
    //pFlow2.getChildren().add(lbPhase);
    //pFlow2.getChildren().add(cbPhase);

    //FlowPane pFlow3 = new FlowPane();
    //pFlow3.getChildren().add(btStart);
    //FlowPane pFlow4 = new FlowPane();
    //pFlow4.getChildren().add(btStop);
    pMain.add(btStart, 4, 0);
    pMain.add(btStop, 5, 0);
    //    pMain.add(pFlow4, 3, 0);
    pMain.add(lbDuration, 6, 0);
    getChildren().add(pMain);


    //setBorder();

  }
}

