package system.presentation.board;

import system.logic.Service;
import system.logic.entities.Project;

import system.logic.entities.Task;
import system.logic.entities.User;
import system.logic.utilities.Priority;
import system.logic.utilities.Status;

import java.time.LocalDate;

public class Controller {
    private final BoardView boardView;
    private final Model model;

    public Controller(BoardView boardView, Model model) {
        this.boardView = boardView;
        this.model = model;

        boardView.setController(this);
        boardView.setModel(model);

        boardView.loadUsers(Service.instance().getAllUsers());

        // To visualize the projects and tasks added in Data Class
        model.setProjectList(Service.instance().getAllProjects());
    }

    public void handleProjectCreation(String description, User manager) throws Exception {
        Service.instance().createProject(description, manager);
        model.setCurrentProject(new Project());
    }

    public void addTaskToSelectedProject(String description, LocalDate date, Priority priority, Status status, User inCharge) throws Exception {
        Service.instance().addTaskToProject(model.getCurrentProject(), description, date, priority, status, inCharge);
        model.setCurrentTask(new Task());
    }

    public void handleSelectedTaskEdition(Priority newPriority, Status newStatus) throws Exception {
        Service.instance().modifyTaskPriorityAndStatus(model.getCurrentTask(), newPriority, newStatus);
        model.setCurrentTask(model.getCurrentTask());
    }
}