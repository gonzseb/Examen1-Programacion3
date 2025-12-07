package system.presentation.board.taskEdition;

import system.Application;

import system.logic.utilities.Priority;
import system.logic.utilities.Status;

import system.presentation.board.Controller;
import system.presentation.board.Model;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class taskEditionView extends JDialog implements PropertyChangeListener {
    private JComboBox selectNewPriorityComboBox;
    private JComboBox selectNewStatusComboBox;
    private JButton cancelButton;
    private JButton saveButton;
    JPanel taskEditionPanel;
    
    // -- MVC --
    private Model model;
    private Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public taskEditionView() {
        setContentPane(taskEditionPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        setLocationRelativeTo(null);
        setTitle("Priority and Status Edition");
        setSize(350, 200);

        selectNewPriorityComboBox.setModel(new DefaultComboBoxModel<>(Priority.values()));
        selectNewStatusComboBox.setModel(new DefaultComboBoxModel<>(Status.values()));

        saveButton.addActionListener(new ActionListener() {
            StringBuilder errors = new StringBuilder();

            @Override
            public void actionPerformed(ActionEvent e) {
                Priority priority = (Priority) selectNewPriorityComboBox.getSelectedItem();
                Status status = (Status) selectNewStatusComboBox.getSelectedItem();

                if (priority == null) {
                    selectNewPriorityComboBox.setBackground(Application.BACKGROUND_ERROR);
                    errors.append("New priority is required.\n");
                }

                if (status == null) {
                    selectNewStatusComboBox.setBackground(Application.BACKGROUND_ERROR);
                    errors.append("New status is required.\n");
                }

                if (!errors.isEmpty()) {
                    JOptionPane.showMessageDialog(taskEditionPanel, errors.toString(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    controller.handleSelectedTaskEdition(priority, status);
                    taskEditionView.this.setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(taskEditionPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskEditionView.this.setVisible(false);
            }
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT_TASK:
                selectNewPriorityComboBox.setSelectedItem((model.getCurrentTask().getPriority()));
                selectNewStatusComboBox.setSelectedItem(model.getCurrentTask().getStatus());
                selectNewPriorityComboBox.setBackground(null);
                selectNewStatusComboBox.setBackground(null);
                break;
        }
        this.taskEditionPanel.revalidate();
    }
}