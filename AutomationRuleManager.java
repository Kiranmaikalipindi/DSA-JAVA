import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class AutomationRule {
    String ruleName;
    int priority;

    public AutomationRule(String ruleName, int priority) {
        this.ruleName = ruleName;
        this.priority = priority;
    }

    public String getRuleName() {
        return ruleName;
    }

    public int getPriority() {
        return priority;
    }
}

public class AutomationRuleManagerGUI extends JFrame {
    private final JTextField ruleNameField = new JTextField(20);
    private final JTextField priorityField = new JTextField(5);
    private final JTextField deleteIndexField = new JTextField(5);
    private final JTextArea outputArea = new JTextArea(10, 40);
    private final java.util.List<AutomationRule> ruleList = new ArrayList<>();

    public AutomationRuleManagerGUI() {
        super("Automation Rule Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Rule Name:"));
        inputPanel.add(ruleNameField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityField);

        JButton addButton = new JButton("Add Rule");
        JButton deleteButton = new JButton("Delete Rule by Index");
        JButton displayButton = new JButton("Display Rules");

        inputPanel.add(addButton);
        inputPanel.add(new JLabel("Delete Index:"));
        inputPanel.add(deleteIndexField);
        inputPanel.add(deleteButton);
        inputPanel.add(displayButton);

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addRule());
        deleteButton.addActionListener(e -> deleteRuleByIndex());
        displayButton.addActionListener(e -> displayRules());
    }

    private void addRule() {
        String name = ruleNameField.getText().trim();
        String priorityText = priorityField.getText().trim();

        if (name.isEmpty() || priorityText.isEmpty()) {
            outputArea.setText("Both Rule Name and Priority are required.");
            return;
        }

        try {
            int priority = Integer.parseInt(priorityText);
            ruleList.add(new AutomationRule(name, priority));
            outputArea.setText("Rule added successfully.");
            ruleNameField.setText("");
            priorityField.setText("");
        } catch (NumberFormatException ex) {
            outputArea.setText("Priority must be a valid integer.");
        }
    }

    private void deleteRuleByIndex() {
        String indexText = deleteIndexField.getText().trim();

        try {
            int index = Integer.parseInt(indexText) - 1;
            if (index >= 0 && index < ruleList.size()) {
                AutomationRule removed = ruleList.remove(index);
                outputArea.setText("Deleted rule: " + removed.getRuleName());
            } else {
                outputArea.setText("Invalid rule number.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter a valid rule number.");
        }
    }

    private void displayRules() {
        if (ruleList.isEmpty()) {
            outputArea.setText("No automation rules found.");
            return;
        }

        ruleList.sort(Comparator.comparingInt(AutomationRule::getPriority));

        StringBuilder sb = new StringBuilder("Automation Rules (sorted by priority):\n");
        for (int i = 0; i < ruleList.size(); i++) {
            AutomationRule rule = ruleList.get(i);
            sb.append((i + 1)).append(". ")
              .append(rule.getRuleName())
              .append(" (Priority: ").append(rule.getPriority()).append(")\n");
        }
        
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutomationRuleManagerGUI().setVisible(true);
        });
    }
}
