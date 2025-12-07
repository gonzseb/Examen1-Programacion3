package system.presentation.board;

import system.logic.entities.Project;
import system.presentation.AbstractTableModel;

import java.util.List;

public class ProjectsTableModel extends AbstractTableModel<Project> implements javax.swing.table.TableModel {
    public ProjectsTableModel(int[] cols, List<Project> rows) {
        super(cols, rows);
    }

    public static final int CODE = 0;
    public static final int DESCRIPTION = 1;
    public static final int MANAGER = 2;
    public static final int PROJECT_TASKS = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[CODE] = "Code";
        colNames[DESCRIPTION] = "Description";
        colNames[MANAGER] = "Manager";
        colNames[PROJECT_TASKS] = "Project Tasks";
    }

    @Override
    protected Object getPropetyAt(Project e, int col) {
        switch (cols[col]) {
            case CODE:
                return e.getCode();
            case DESCRIPTION:
                return e.getDescription();
            case MANAGER:
                return e.getManager();
            case PROJECT_TASKS:
                return e.getTaskList().size();
            default:
                return "";
        }
    }
}