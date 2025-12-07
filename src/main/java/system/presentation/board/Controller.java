package system.presentation.board;

import system.logic.Service;
import system.logic.entities.Project;
import system.logic.entities.Task;

import system.logic.entities.User;
import system.logic.utilities.Priority;
import system.logic.utilities.Status;

public class Controller {
    private final boardView boardView;
    private final Model model;

    public Controller(boardView boardView, Model model) {
        this.boardView = boardView;
        this.model = model;

        boardView.setController(this);
        boardView.setModel(model);

        boardView.loadUsers(Service.instance().getAllUsers());

        initializeProjectsView(); // ComboBoxes en null
        clearTaskForm();
    }

    // Initializes/refreshes the list of projects and clears the selected one
    public void initializeProjectsView() {
        model.setCurrentProject(new Project());
        model.setProjectList(Service.instance().getAllProjects());
    }

    // Clears the data in the Task form model state
    public void clearTaskForm() {
        model.setCurrentTask(new Task());
    }

    public void handleProjectCreation(String description, User manager) throws Exception {
        // 1. Validar y crear en el controller
        Service.instance().createProject(description, manager);

        // 4. Refrescar vista
        initializeProjectsView();
    }


    // Handles adding a task to the project currently selected in the Model
    public void addTaskToSelectedProject(String description, String date, Priority priority, Status status, User inCharge) throws Exception {
        // 1. Tell the service to save the task using the currently selected project
        Service.instance().addTaskToProject(model.getCurrentProject(), description, date, priority, status, inCharge);

        // 2. Refresh UI by clearing the task form and relying on the Model to fire property changes
        clearTaskForm();

        // Since the Project object in the Model is the same one in the Service's data,
        // you only need to notify the boardView that the list of tasks for the selected project has changed.
        // The boardView should react to this by updating the Task Table (PROJECT_TASKS).
        model.setCurrentProject(model.getCurrentProject()); // Re-set the project to fire the update for PROJECT_TASKS
    }

    public void handleSelectedTaskEdition(Priority newPriority, Status newStatus) throws Exception {
        Service.instance().modifyTaskPriorityAndStatus(model.getCurrentTask(), newPriority, newStatus);
        model.setCurrentTask(model.getCurrentTask());
    }
}