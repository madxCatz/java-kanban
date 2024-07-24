import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {

    int id = 1;
    private final HistoryManager historyManager;

    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();

        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    private int generateId() {
        return id++;
    }

    private void checkStatus(Epic epic) {
        if ((epic.getSubtaskIds()).isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        int counter = -1;
        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);

            if (TaskStatus.IN_PROGRESS.equals(subtask.getStatus())) {
                counter = 1;
                break;
            } else if (TaskStatus.DONE.equals(subtask.getStatus())) {
                if (counter == 0) {
                    counter = 1;
                    break;
                }
                counter = 2;
            } else if (TaskStatus.NEW.equals(subtask.getStatus())) {
                if (counter == 2) {
                    counter = 1;
                    break;
                }
                counter = 0;
            }
        }
        switch (counter) {
            case 1:
                epic.setStatus(TaskStatus.IN_PROGRESS);
                break;
            case 2:
                epic.setStatus(TaskStatus.DONE);
                break;
            default:
                epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public void add(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void add(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void add(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        epic.addNewSubtaskId(subtask.getId());
        checkStatus(epic);
    }

    @Override
    public void update(Task task, int correctId) {
        task.setId(correctId);
        tasks.put(task.getId(), task);
    }

    @Override
    public void update(Epic epic, int correctId) {
        epic.setId(correctId);
        epics.put(epic.getId(), epic);
        checkStatus(epic);
    }

    @Override
    public void update(Subtask subtask, int correctId) {
        subtask.setId(correctId);
        subtasks.put(subtask.getId(), subtask);
        checkStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public Task getTask(int taskId) {
        historyManager.add(tasks.get(taskId));

        return tasks.get(taskId);
    }

    @Override
    public Epic getEpic(int epicId) {
        historyManager.add(epics.get(epicId));

        return epics.get(epicId);
    }

    @Override
    public Subtask getSubtask(int subtaskId) {
        historyManager.add(subtasks.get(subtaskId));

        return subtasks.get(subtaskId);
    }

    @Override
    public void removeTask(int taskId) {
        tasks.remove(taskId);

        historyManager.remove(taskId);
    }

    @Override
    public void removeEpic(int epicId) {
        Epic epic = epics.get(epicId);

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
        epics.remove(epicId);

        historyManager.remove(epicId);
    }

    @Override
    public void removeSubtask(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        Epic epic = epics.get(subtask.getEpicId());

        epic.getSubtaskIds().remove((Integer) subtaskId);
        subtasks.remove(subtaskId);
        checkStatus(epic);

        historyManager.remove(subtaskId);
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> subtaskList = new ArrayList<>();
        Epic epic = epics.get(epicId);

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtaskList.add(subtasks.get(subtaskId));
            historyManager.add(subtasks.get(subtaskId));
        }

        return subtaskList;
    }

    @Override
    public List<Task> getAllTasks() {

        List<Task> taskList = new ArrayList<>(tasks.values());
        for (Task task : taskList) {
            historyManager.add(task);
        }

        return taskList;
    }

    @Override
    public List<Epic> getAllEpics() {

        List<Epic> epicList = new ArrayList<>(epics.values());
        for (Task epic : epicList) {
            historyManager.add(epic);
        }

        return epicList;
    }

    @Override
    public List<Subtask> getAllSubtasks() {

        List<Subtask> subtaskList = new ArrayList<>(subtasks.values());
        for (Task subtask : subtaskList) {
            historyManager.add(subtask);
        }

        return subtaskList;
    }

    @Override
    public void removeAllTasks() {

        List<Task> taskList = new ArrayList<>(tasks.values());
        for (Task task : taskList) {
            historyManager.remove(task.getId());
        }

        tasks.clear();
    }

    @Override
    public void removeAllEpics() {

        List<Epic> epicList = new ArrayList<>(epics.values());
        for (Epic epic : epicList) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                historyManager.remove(subtaskId);
            }
            historyManager.remove(epic.getId());
        }

        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtasks() {

        List<Subtask> subtaskList = new ArrayList<>(subtasks.values());
        for (Subtask subtask : subtaskList) {
            historyManager.remove(subtask.getId());
        }

        subtasks.clear();
        for (Epic epic : epics.values()) {
            (epic.getSubtaskIds()).clear();
            checkStatus(epic);
        }
    }

}