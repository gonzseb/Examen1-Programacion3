package system.presentation.board;

import system.logic.Service;
import system.logic.entities.Project;
import system.logic.entities.Task;
import system.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Project currentProject;
    private List<Project> projectList;
    private Task currentTask;

    public static final String CURRENT_PROJECT = "currentProject";
    public static final String PROJECT_LIST = "projectList";
    public static final String CURRENT_TASK = "currentTask";
    public static final String PROJECT_TASKS = "projectTasks";

    public Model() {
        currentProject = new Project();
        projectList = new ArrayList<>();
        currentTask = new Task();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT_PROJECT);
        firePropertyChange(PROJECT_LIST);
        firePropertyChange(CURRENT_TASK);
        firePropertyChange(PROJECT_TASKS);
    }

    // --- Getters ---

    public Project getCurrentProject() {
        return currentProject;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    // --- Setters ---

    public void setCurrentProject(Project project) {
        this.currentProject = project;
        firePropertyChange(CURRENT_PROJECT);
        setProjectList(Service.instance().getAllProjects());
        firePropertyChange(PROJECT_TASKS);
    }

    public void setCurrentTask(Task task) {
        this.currentTask = task;
        firePropertyChange(CURRENT_TASK);
        firePropertyChange(PROJECT_TASKS);
    }

    public void setProjectList(List<Project> list) {
        this.projectList = list;
        firePropertyChange(PROJECT_LIST);
    }
}