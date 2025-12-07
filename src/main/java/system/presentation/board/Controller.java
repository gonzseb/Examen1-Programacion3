package system.presentation.board;

import system.logic.Service;
import system.logic.entities.Project;
import system.logic.entities.Task;

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

        // Initial setup methods
        initializeProjectsView();
        clearTaskForm();
    }

    // Initializes/refreshes the list of projects and clears the selected one
    public void initializeProjectsView() {
        model.setSelectedProject(new Project());
        model.setProjectList(Service.instance().getAllProjects());
    }

    // Clears the data in the Task form model state
    public void clearTaskForm() {
        model.setCurrentTaskForm(new Task());
    }

    // Handles the creation of a new project from the boardView's input
    public void handleProjectCreation(Project project) throws Exception {
        Service.instance().createProject(project);
        initializeProjectsView(); // Refreshes the list to show the new project
    }

    // Handles adding a task to the project currently selected in the Model
    public void addTaskToSelectedProject(Task task) throws Exception {
        // 1. Tell the service to save the task using the currently selected project
        Service.instance().addTaskToProject(model.getSelectedProject(), task);

        // 2. Refresh UI by clearing the task form and relying on the Model to fire property changes
        clearTaskForm();

        // Since the Project object in the Model is the same one in the Service's data,
        // you only need to notify the boardView that the list of tasks for the selected project has changed.
        // The boardView should react to this by updating the Task Table (PROJECT_TASKS).
        model.setSelectedProject(model.getSelectedProject()); // Re-set the project to fire the update for PROJECT_TASKS
    }

    public void handleSelectedTaskEdition(Priority newPriority, Status newStatus) throws Exception {
        Service.instance().modifyTaskPriorityAndStatus(model.getSelectedTask(), newPriority, newStatus);
        model.setSelectedTask(model.getSelectedTask());
    }
}