package view.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsPanel extends JPanel {


    final JPanel pHeader;

    final JComboBox<String> cbPeriod;

    final StatisticsTableModel tblProjectsModel;
    final JTable tblProjects;

    public StatisticsPanel() {
        super(new BorderLayout(5,5));
        this.pHeader = new JPanel(new FlowLayout(5));

        this.cbPeriod = new JComboBox<>();
        this.tblProjectsModel = new StatisticsTableModel("Projectname");
        this.tblProjects = new JTable(tblProjectsModel);

        tblProjects.setBorder(new LineBorder(Color.black, 1));
        pHeader.add(cbPeriod);
        add(pHeader, BorderLayout.NORTH);
        add(tblProjects, BorderLayout.CENTER);

    }


}
