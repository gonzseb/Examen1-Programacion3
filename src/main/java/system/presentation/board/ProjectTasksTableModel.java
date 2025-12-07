package system.presentation.board;

import system.logic.entities.Task;
import system.presentation.AbstractTableModel;

import java.util.List;

public class ProjectTasksTableModel extends AbstractTableModel<Task> implements javax.swing.table.TableModel {
    public ProjectTasksTableModel(int[] cols, List<Task> rows) {
        super(cols, rows);
    }

    public static final int NUMBER = 0;
    public static final int DESCRIPTION = 1;
    public static final int SPECTED_END_DATE = 2;
    public static final int PRIORITY = 3;
    public static final int STATUS = 4;
    public static final int PERSON_IN_CHARGE = 5;

    @Override
    protected void initColNames() {
        colNames = new String[6];
        colNames[NUMBER] = "Number";
        colNames[DESCRIPTION] = "Description";
        colNames[SPECTED_END_DATE] = "Spected End Date";
        colNames[PRIORITY] = "Priority";
        colNames[STATUS] = "Status";
        colNames[PERSON_IN_CHARGE] = "Person in Charge";
    }

    @Override
    protected Object getPropetyAt(Task t, int col) {
        switch (cols[col]) {
            case NUMBER:
                return t.getNumber();

            case DESCRIPTION:
                return t.getDescription();

            case SPECTED_END_DATE:
                return t.getSpectedEndDate();

            case PRIORITY:
                return t.getPriority();

            case STATUS:
                return t.getStatus();

            case PERSON_IN_CHARGE:
                return t.getPersonInCharge();

            default:
                return "";
        }
    }
}