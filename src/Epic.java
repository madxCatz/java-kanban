import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtaskIds;

    public Epic(String taskName, String description, TaskStatus status) {
        super(taskName, description, status);
        subtaskIds = new ArrayList<>();
    }

    public void addNewSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "epicName='" + getTaskName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}