import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void add(Task task);

    void add(Epic epic);

    void add(Subtask subtask);

    void update(Task task, int correctId);

    void update(Epic epic, int correctId);

    void update(Subtask subtask, int correctId);

    Task getTask(int taskId);

    Epic getEpic(int epicId);

    Subtask getSubtask(int subtaskId);

    void removeTask(int taskId);

    void removeEpic(int epicId);

    void removeSubtask(int subtaskId);

    ArrayList<Subtask> getEpicSubtasks(int epicId);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    List<Task> getHistory();
}