package system.presentation.board;

import system.logic.entities.Project;
import system.logic.entities.Task;
import system.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Project selectedProject;
    private List<Project> projectList;
    private Task currentTaskForm;
    private Task selectedTask;

    public static final String SELECTED_PROJECT = "selectedProject";
    public static final String PROJECT_LIST = "projectList";
    public static final String CURRENT_TASK_FORM = "currentTaskForm";
    public static final String PROJECT_TASKS = "projectTasks";
    public static final String SELECTED_TASK = "selectedTask";

    public Model() {
        selectedProject = new Project();
        projectList = new ArrayList<>();
        currentTaskForm = new Task();
        selectedTask = new Task();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);

        firePropertyChange(SELECTED_PROJECT);
        firePropertyChange(PROJECT_LIST);
        firePropertyChange(CURRENT_TASK_FORM);
        firePropertyChange(PROJECT_TASKS);
        firePropertyChange(SELECTED_TASK);
    }

    // --- Getters ---

    public Project getSelectedProject() {
        return selectedProject;
    }

    public Task getCurrentTaskForm() {
        return currentTaskForm;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public Task getSelectedTask() { return selectedTask; }

    // --- Setters ---

    public void setSelectedProject(Project project) {
        this.selectedProject = project;
        firePropertyChange(SELECTED_PROJECT);
        firePropertyChange(PROJECT_TASKS);
    }

    public void setCurrentTaskForm(Task task) {
        this.currentTaskForm = task;
        firePropertyChange(CURRENT_TASK_FORM);
    }

    public void setProjectList(List<Project> list) {
        this.projectList = list;
        firePropertyChange(PROJECT_LIST);
    }

    public void setSelectedTask(Task task) {
        this.selectedTask = task;
        firePropertyChange(SELECTED_TASK);
    }
}