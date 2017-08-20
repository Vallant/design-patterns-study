package view.javafx.personalstatistic;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import view.javafx.projectstatistic.ProjectStatisticActivityTableData;

public class PersonalStatisticActivityPane extends BorderPane
{
  private final BorderPane pHeader;
  private final GridPane   pHeaderGrid;

  final ComboBox<String> cbPeriod;

  final         TableView  tblActivity;
  private final ScrollPane scrpTable;
  final         Button     btBack;

  private final GridPane pButtons;
  final Button   btDeleteActivity;
  final Button   btAddActivity;
  final Button   btUpdateActivity;

  public PersonalStatisticActivityPane()
  {
    pHeader = new BorderPane();
    pHeaderGrid = new GridPane();


    cbPeriod = new ComboBox<>();
    cbPeriod.getItems().add("All Time");
    cbPeriod.getItems().add("Last Year");
    cbPeriod.getItems().add("Last Month");
    cbPeriod.getItems().add("Last Week");
    cbPeriod.getItems().add("Last Day");
    cbPeriod.getSelectionModel().selectFirst();

    btBack = new Button("Back");

    tblActivity = new TableView();
    scrpTable = new ScrollPane(tblActivity);

    pButtons = new GridPane();
    btAddActivity = new Button("Add Activity");
    btDeleteActivity = new Button("Delete Activity");
    btUpdateActivity = new Button("Update Activity");

    pButtons.add(btAddActivity, 0, 0);
    pButtons.add(btDeleteActivity, 0, 1);
    pButtons.add(btUpdateActivity, 0, 2);

    pHeaderGrid.add(btBack, 0, 0);
    pHeaderGrid.add(cbPeriod, 1, 0);
    pHeader.setLeft(pHeaderGrid);
    setTop(pHeader);
    setCenter(scrpTable);
    setRight(pButtons);

    TableColumn<ProjectStatisticActivityTableData, String> startColumn = new TableColumn<>("Start");
    startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));


    TableColumn<ProjectStatisticActivityTableData, String> endColumn = new TableColumn<>("End");
    endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

    TableColumn<ProjectStatisticActivityTableData, String> commentColumn = new TableColumn<>("Comment");
    commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

    TableColumn<ProjectStatisticActivityTableData, String> descriptionColumn = new TableColumn<>("Description");
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    tblActivity.getColumns().addAll(startColumn, endColumn, descriptionColumn, commentColumn);
    tblActivity.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    scrpTable.setFitToWidth(true);
    scrpTable.setFitToHeight(true);

    scrpTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrpTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
  }
}
