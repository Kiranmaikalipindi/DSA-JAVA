class AutomationRule {
    private String ruleName;
    private AutomationRule next;

    public AutomationRule(String ruleName) {
        this.ruleName = ruleName;
        this.next = null;
    }

    public String getRuleName() {
        return ruleName;
    }

    public AutomationRule getNext() {
        return next;
    }

    public void setNext(AutomationRule next) {
        this.next = next;
    }
}

public class AutomationRuleManager {
    private AutomationRule head;
    private AutomationRule tail;

    public void addRule(String ruleName) {
        if (ruleName == null || ruleName.trim().isEmpty()) {
            System.out.println("Rule name cannot be null or empty.");
            return;
        }
        if (isRuleExists(ruleName)) {
            System.out.println("Rule '" + ruleName + "' already exists.");
            return;
        }

        AutomationRule newRule = new AutomationRule(ruleName);
        if (head == null) {
            head = newRule;
            tail = newRule;
        } else {
            tail.setNext(newRule);
            tail = newRule;
        }
        System.out.println("Rule '" + ruleName + "' added successfully.");
    }

    private boolean isRuleExists(String ruleName) {
        AutomationRule current = head;
        while (current != null) {
            if (current.getRuleName().equals(ruleName)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public void deleteRule(String ruleName) {
        AutomationRule current = head, prev = null;
        while (current != null && !current.getRuleName().equals(ruleName)) {
            prev = current;
            current = current.getNext();
        }
        if (current != null) {
            if (prev != null) {
                prev.setNext(current.getNext());
            } else {
                head = current.getNext();
            }
            System.out.println("Rule '" + ruleName + "' deleted successfully.");
        } else {
            System.out.println("Rule '" + ruleName + "' not found.");
        }
    }

    public void displayRules() {
        if (head == null) {
            System.out.println("No automation rules found.");
            return;
        }
        AutomationRule current = head;
        System.out.println("Automation Rules:");
        while (current != null) {
            System.out.println("- " + current.getRuleName());
            current = current.getNext();
        }
    }

    public static void main(String[] args) {
        AutomationRuleManager manager = new AutomationRuleManager();
        manager.addRule("Turn on lights when motion detected");
        manager.addRule("Turn on lights when motion detected");
        manager.addRule("Turn off lights after 10 minutes");
        manager.displayRules();
        manager.deleteRule("Turn on lights when motion detected");
        manager.deleteRule("Non-existing Rule");
        manager.displayRules();
    }
}
