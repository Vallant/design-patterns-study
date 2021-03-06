package view.javafx.personalstatistic;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import view.javafx.projectstatistic.ProjectStatisticTableData;

class PersonalStatisticPhasePane extends BorderPane
{

  final ComboBox<String> cbPeriod;

  final TableView tblPhases;
  final Button    btBack;

  public PersonalStatisticPhasePane()
  {
    BorderPane pHeader = new BorderPane();
    GridPane pHeaderGrid = new GridPane();
    pHeaderGrid.setHgap(5);
    pHeader.setPadding(new Insets(5, 0, 5, 0));


    cbPeriod = new ComboBox<>();
    cbPeriod.getItems().add("All Time");
    cbPeriod.getItems().add("Last Year");
    cbPeriod.getItems().add("Last Month");
    cbPeriod.getItems().add("Last Week");
    cbPeriod.getItems().add("Last Day");
    cbPeriod.getSelectionModel().clearAndSelect(0);


    btBack = new Button("Back");

    tblPhases = new TableView();
    ScrollPane scrpTable = new ScrollPane(tblPhases);

    pHeaderGrid.add(btBack, 0, 0);
    pHeaderGrid.add(cbPeriod, 1, 0);
    pHeader.setLeft(pHeaderGrid);
    setTop(pHeader);
    setCenter(scrpTable);

    TableColumn<ProjectStatisticTableData, String> nameColumn = new TableColumn<>("Phase Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
    TableColumn<ProjectStatisticTableData, String> durationColumn = new TableColumn<>("Duration");
    durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    TableColumn<ProjectStatisticTableData, String> shareColumn = new TableColumn<>("Share");
    shareColumn.setCellValueFactory(new PropertyValueFactory<>("share"));

    tblPhases.getColumns().add(nameColumn);
    tblPhases.getColumns().add(shareColumn);
    tblPhases.getColumns().add(durationColumn);

    tblPhases.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    scrpTable.setFitToWidth(true);
    scrpTable.setFitToHeight(true);

    scrpTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrpTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
  }
}
