package view.swing.project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stephan on 11/07/17.
 */
public class ProjectDetailAddMemberPanel extends JPanel {
    private ArrayList<String> available;

    private final DefaultListModel<String> lstAvailableUsersModel;
    final JList<String> lstAvailableUsers;


    public ProjectDetailAddMemberPanel() {
        super(new BorderLayout(5,5));

        lstAvailableUsersModel = new DefaultListModel<>();
        lstAvailableUsers = new JList<>(lstAvailableUsersModel);
        lstAvailableUsers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        add(lstAvailableUsers, BorderLayout.CENTER);
    }

    void setAvailableNames(ArrayList<String> names)
    {
        for(String s : names)
            lstAvailableUsersModel.addElement(s);
    }


}
