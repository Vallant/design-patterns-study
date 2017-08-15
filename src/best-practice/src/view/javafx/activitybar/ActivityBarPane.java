package view.javafx.activitybar;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class ActivityBarPane extends FlowPane
{
  private final GridPane         pMain;
  final         ComboBox<String> cbProject;
  private final Label            lbProject;
  final         ComboBox<String> cbPhase;
  private final Label            lbPhase;
  final         Button           btStart;
  final         Button          btStop;
  final Label           lbDuration;

  public ActivityBarPane()
  {
    setHgap(5);
    setPadding(new Insets(5,5,5,5));

    pMain = new GridPane();
    pMain.setPadding(new Insets(5,5,5,5));

    FlowPane pFlow1 = new FlowPane();
    cbProject = new ComboBox<>();

    lbProject = new Label("Project:");

    FlowPane pFlow2 = new FlowPane();
    cbPhase = new ComboBox<>();
    lbPhase = new Label("Phase:");

    btStart = new Button("Start Activity");
    btStop = new Button("Stop Activity");
    lbDuration = new Label();
    lbDuration.setPrefWidth(50);
    btStart.setDisable(true);
    btStop.setDisable(true);


    pFlow1.getChildren().add(lbProject);
    pFlow1.getChildren().add(cbProject);
    pMain.add(pFlow1, 0,0);
    pFlow2.getChildren().add(lbPhase);
    pFlow2.getChildren().add(cbPhase);

    FlowPane pFlow3 = new FlowPane();
    pFlow3.getChildren().add(btStart);
    FlowPane pFlow4 = new FlowPane();
    pFlow4.getChildren().add(btStop);
    pMain.add(pFlow2, 1, 0);
    pMain.add(pFlow3, 2, 0);
    pMain.add(pFlow4, 3, 0);
    pMain.add(lbDuration, 4, 0);
    getChildren().add(pMain);


    //setBorder();

  }
}

