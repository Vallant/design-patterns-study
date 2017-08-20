package view.javafx.personalstatistic;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import view.javafx.projectstatistic.ProjectStatisticTableData;

public class PersonalStatisticPhasePane extends BorderPane
{

  private final BorderPane pHeader;
  private final GridPane   pHeaderGrid;

  final ComboBox<String> cbPeriod;

  final         TableView  tblPhases;
  private final ScrollPane scrpTable;
  final         Button     btBack;

  public PersonalStatisticPhasePane()
  {
    pHeader = new BorderPane();
    pHeaderGrid = new GridPane();


    cbPeriod = new ComboBox<>();
    cbPeriod.getItems().add("All Time");
    cbPeriod.getItems().add("Last Year");
    cbPeriod.getItems().add("Last Month");
    cbPeriod.getItems().add("Last Week");
    cbPeriod.getItems().add("Last Day");



    btBack = new Button("Back");

    tblPhases = new TableView();
    scrpTable = new ScrollPane(tblPhases);

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
