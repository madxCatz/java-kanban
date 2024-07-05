import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {

    private final List<Integer> subtaskIds;

    public Epic(String taskName, String description, TaskStatus status) {
        super(taskName, description, status);
        subtaskIds = new ArrayList<>();
    }

    public void addNewSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public List<Integer> getSubtaskIds() {
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