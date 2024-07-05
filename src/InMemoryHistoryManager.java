import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> taskHistory;
    private static final int MAX_SIZE_HISTORY = 10;

    public InMemoryHistoryManager() {
        taskHistory = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (taskHistory.size() == MAX_SIZE_HISTORY) {
            taskHistory.removeFirst();
        }
        taskHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }
}