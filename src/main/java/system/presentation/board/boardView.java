package system.presentation.board;

import system.Application;
import system.logic.entities.Project;
import system.logic.entities.Task;
import system.logic.entities.User;
import system.logic.utilities.Priority;
import system.logic.utilities.Status;

import javax.swing.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import system.presentation.board.taskEdition.taskEditionView;

public class boardView implements PropertyChangeListener {
    private JPanel boardPanel;
    private JButton createProjectButton;
    private JTextField projectDescriptionTextField;
    private JComboBox managerComboBox;
    private JTable projects;
    private JTable tasks;
    private JLabel projectSelected;
    private JButton createTaskButton;
    private JTextField taskDescriptionTextField;
    private JTextField spectedEndDateTextField;
    private JComboBox priorityComboBox;
    private JComboBox statusComboBox;
    private JComboBox personInChargeComboBox;
    private JPanel createTaskPanel;

    private taskEditionView taskEditionWindow;

    // -- MVC --
    public void loadUsers(List<User> users) {
        DefaultComboBoxModel<User> managerModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<User> personModel = new DefaultComboBoxModel<>();

        for (User u : users) {
            managerModel.addElement(u);
            personModel.addElement(u);
        }

        managerComboBox.setModel(managerModel);
        personInChargeComboBox.setModel(personModel);
    }

    private Model model;
    private Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        taskEditionWindow.setModel(model);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        taskEditionWindow.setController(controller);
    }

    public boardView() {
        taskEditionWindow = new taskEditionView();

        projectDescriptionTextField.setToolTipText("Write a description");
        managerComboBox.setToolTipText("Select a manager");

        projectSelected.setText("Project Selected ID: Empty");

        taskDescriptionTextField.setToolTipText("Write a description");
        spectedEndDateTextField.setToolTipText("Write a date. Ex: mm-dd-yyyy");

        priorityComboBox.setModel(new DefaultComboBoxModel<>(Priority.values()));
        statusComboBox.setModel(new DefaultComboBoxModel<>(Status.values()));

        createTaskPanel.setVisible(false);

        createProjectButton.addActionListener(e -> {
            resetFieldStylesProjectForm();

            String description = projectDescriptionTextField.getText().trim();
            User selectedManager = (User) managerComboBox.getSelectedItem();

            StringBuilder errors = new StringBuilder();

            if (description.isEmpty()) {
                projectDescriptionTextField.setBackground(Application.BACKGROUND_ERROR);
                projectDescriptionTextField.setToolTipText("Description is required");
                errors.append("Description is required.\n");
            }

            if (selectedManager == null) {
                managerComboBox.setBackground(Application.BACKGROUND_ERROR);
                errors.append("A manager must be selected.\n");
            }

            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(boardPanel, errors.toString(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                controller.handleProjectCreation(new Project(description, selectedManager));
                clearProjectForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(boardPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        projects.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = projects.getSelectedRow();

                if (selectedRow >= 0) {
                    Project seleccionado = ((ProjectsTableModel) projects.getModel()).getRowAt(selectedRow);
                    model.setSelectedProject(seleccionado);
                    projectSelected.setText("Project Selected ID: " + seleccionado.getCode());
                    createTaskPanel.setVisible(true);
                }
            }
        });

        createTaskButton.addActionListener(e -> {
            resetFieldStylesTaskForm();

            String description = taskDescriptionTextField.getText().trim();
            String date = spectedEndDateTextField.getText().trim();
            Priority priority = (Priority) priorityComboBox.getSelectedItem();
            Status status = (Status) statusComboBox.getSelectedItem();
            User inCharge = (User) personInChargeComboBox.getSelectedItem();

            StringBuilder errors = new StringBuilder();

            if (model.getSelectedProject().getCode().isEmpty()) {
                projectSelected.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Project must be selected.\n");
            }

            if (description.isEmpty()) {
                taskDescriptionTextField.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Description is required.\n");
            }

            if (date.isEmpty()) {
                spectedEndDateTextField.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Expected end date is required.\n");
            }

            if (priority == null) {
                priorityComboBox.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Priority must be selected.\n");
            }

            if (status == null) {
                statusComboBox.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Status must be selected.\n");
            }

            if (inCharge == null) {
                personInChargeComboBox.setBackground(Application.BACKGROUND_ERROR);
                errors.append("A person in charge must be selected.\n");
            }

            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(boardPanel, errors.toString(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Task newTask = new Task(description, date, priority, status, inCharge);
                controller.addTaskToSelectedProject(newTask);
                clearTaskForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(boardPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tasks.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Detect double-click
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();

                    int selectedRow = tasks.getSelectedRow();

                    if (selectedRow >= 0) {
                        Task selectedTask = ((ProjectTasksTableModel) tasks.getModel()).getRowAt(selectedRow);

                        model.setSelectedTask(selectedTask);

                        taskEditionWindow.setVisible(true);
                    }
                }
            }
        });
    }

    public JPanel getPanel() { return boardPanel; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.SELECTED_PROJECT:
                projectDescriptionTextField.setText(model.getSelectedProject().getDescription());
                managerComboBox.setSelectedItem(model.getSelectedProject().getManager());
                projectDescriptionTextField.setBackground(null);
                managerComboBox.setBackground(null);
                break;

            case Model.PROJECT_LIST:
                int[] cols = {
                        ProjectsTableModel.CODE,
                        ProjectsTableModel.DESCRIPTION,
                        ProjectsTableModel.MANAGER,
                        ProjectsTableModel.PROJECT_TASKS
                };

                projects.setModel(new ProjectsTableModel(cols, model.getProjectList()));
                break;

            case Model.CURRENT_TASK_FORM:
                taskDescriptionTextField.setText(model.getCurrentTaskForm().getDescription());
                spectedEndDateTextField.setText(model.getCurrentTaskForm().getSpectedEndDate());
                priorityComboBox.setSelectedItem((model.getCurrentTaskForm().getPriority()));
                statusComboBox.setSelectedItem(model.getCurrentTaskForm().getStatus());
                personInChargeComboBox.setSelectedItem(model.getCurrentTaskForm().getPersonInCharge());
                priorityComboBox.setBackground(null);
                statusComboBox.setBackground(null);
                personInChargeComboBox.setBackground(null);
                break;

            case Model.PROJECT_TASKS:
                int[] taskCols = {
                        ProjectTasksTableModel.NUMBER,
                        ProjectTasksTableModel.DESCRIPTION,
                        ProjectTasksTableModel.SPECTED_END_DATE,
                        ProjectTasksTableModel.PRIORITY,
                        ProjectTasksTableModel.STATUS,
                        ProjectTasksTableModel.PERSON_IN_CHARGE
                };

                tasks.setModel(new ProjectTasksTableModel(taskCols, model.getSelectedProject().getTaskList()));
                break;

            case Model.SELECTED_TASK:
                taskDescriptionTextField.setText(model.getSelectedTask().getDescription());
                spectedEndDateTextField.setText(model.getSelectedTask().getSpectedEndDate());
                priorityComboBox.setSelectedItem(model.getSelectedTask().getPriority());
                statusComboBox.setSelectedItem(model.getSelectedTask().getStatus());
                personInChargeComboBox.setSelectedItem(model.getSelectedTask().getPersonInCharge());

                taskDescriptionTextField.setBackground(null);
                spectedEndDateTextField.setBackground(null);
                priorityComboBox.setBackground(null);
                statusComboBox.setBackground(null);
                personInChargeComboBox.setBackground(null);

                break;

        }
        this.boardPanel.revalidate();
    }

    // -- Helpers --

    private void resetFieldStylesProjectForm() {
        projectDescriptionTextField.setBackground(null);
        managerComboBox.setBackground(null);
    }

    private void resetFieldStylesTaskForm() {
        taskDescriptionTextField.setBackground(null);
        spectedEndDateTextField.setBackground(null);
        priorityComboBox.setBackground(null);
        statusComboBox.setBackground(null);
        personInChargeComboBox.setBackground(null);
    }

    private void clearProjectForm() {
        projectDescriptionTextField.setText("");
        managerComboBox.setSelectedIndex(-1);
        projectSelected.setText("Project Selected ID: Empty");
    }

    private void clearTaskForm() {
        taskDescriptionTextField.setText("");
        spectedEndDateTextField.setText("");
        priorityComboBox.setSelectedIndex(-1);
        statusComboBox.setSelectedIndex(-1);
        personInChargeComboBox.setSelectedIndex(-1);
    }
}