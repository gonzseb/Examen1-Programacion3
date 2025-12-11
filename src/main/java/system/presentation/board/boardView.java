package system.presentation.board;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import system.Application;
import system.logic.entities.Project;
import system.logic.entities.Task;
import system.logic.entities.User;
import system.logic.utilities.Priority;
import system.logic.utilities.Status;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
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
    private JComboBox priorityComboBox;
    private JComboBox statusComboBox;
    private JComboBox personInChargeComboBox;
    private JPanel createTaskPanel;
    private DatePicker endDatePicker;
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
        taskEditionWindow.setModel(model);
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        taskEditionWindow.setController(controller);
    }

    public boardView() {
        endDatePicker.getSettings().setDateRangeLimits(LocalDate.now(), null); // From now to infinite

        endDatePicker.setBackground(Color.lightGray);

        endDatePicker.getSettings().setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, Color.LIGHT_GRAY);
        endDatePicker.getSettings().setColor(DatePickerSettings.DateArea.TextTodayLabel, Color.BLACK);
        endDatePicker.getSettings().setColor(DatePickerSettings.DateArea.TextClearLabel, Color.BLACK);
        endDatePicker.getSettings().setColor(DatePickerSettings.DateArea.TextMonthAndYearMenuLabels, Color.BLACK);

        taskEditionWindow = new taskEditionView();

        projectDescriptionTextField.setToolTipText("Write a description");
        managerComboBox.setToolTipText("Select a manager");

        projectSelected.setText("Project Selected ID: Empty");

        taskDescriptionTextField.setToolTipText("Write a description");
        endDatePicker.setToolTipText("Write a date. Ex: mm-dd-yyyy");

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
                controller.handleProjectCreation(description, selectedManager);
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
                    model.setCurrentProject(seleccionado);
                    projectSelected.setText("Project Selected ID: " + seleccionado.getCode());
                    createTaskPanel.setVisible(true);
                }
            }
        });

        createTaskButton.addActionListener(e -> {
            resetFieldStylesTaskForm();

            String description = taskDescriptionTextField.getText().trim();
            LocalDate date = endDatePicker.getDate();
            Priority priority = (Priority) priorityComboBox.getSelectedItem();
            Status status = (Status) statusComboBox.getSelectedItem();
            User inCharge = (User) personInChargeComboBox.getSelectedItem();

            StringBuilder errors = new StringBuilder();

            if (model.getCurrentProject().getCode().isEmpty()) {
                projectSelected.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Project must be selected.\n");
            }

            if (description.isEmpty()) {
                taskDescriptionTextField.setBackground(Application.BACKGROUND_ERROR);
                errors.append("Description is required.\n");
            }

            if (endDatePicker.getDate() == null) {
                endDatePicker.setBackground(Application.BACKGROUND_ERROR);
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
                controller.addTaskToSelectedProject(description, date, priority, status, inCharge);
                clearTaskForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(boardPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tasks.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tasks.getSelectedRow();

                if (selectedRow >= 0) {
                    Task selected = ((ProjectTasksTableModel) tasks.getModel()).getRowAt(selectedRow);
                    taskEditionWindow.setTitle(selected.getNumber() + " Priority and Status Edition");
                    model.setCurrentTask(selected);
                }
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
            case Model.CURRENT_PROJECT:
                projectDescriptionTextField.setText(model.getCurrentProject().getDescription());
                managerComboBox.setSelectedItem(model.getCurrentProject().getManager());
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

            case Model.CURRENT_TASK:
                taskDescriptionTextField.setText(model.getCurrentTask().getDescription());
                endDatePicker.setDate(model.getCurrentTask().getSpectedEndDate());
                priorityComboBox.setSelectedItem((model.getCurrentTask().getPriority()));
                statusComboBox.setSelectedItem(model.getCurrentTask().getStatus());
                personInChargeComboBox.setSelectedItem(model.getCurrentTask().getPersonInCharge());

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

                tasks.setModel(new ProjectTasksTableModel(taskCols, model.getCurrentProject().getTaskList()));
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
        endDatePicker.setBackground(null);
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
        endDatePicker.setText("");
        priorityComboBox.setSelectedIndex(-1);
        statusComboBox.setSelectedIndex(-1);
        personInChargeComboBox.setSelectedIndex(-1);
    }
}