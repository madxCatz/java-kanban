import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {

    int nextId = 1;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    private int generateNextId() {
        return nextId++;
    }

    private void checkStatus(Epic epic) {
        if ((epic.getSubtaskIds()).isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        int counter = 0;
        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (TaskStatus.IN_PROGRESS.equals(subtask.getStatus())) {
                counter++;
            } else if (TaskStatus.DONE.equals(subtask.getStatus())) {
                counter += 2;
            }
        }

        if (counter == 0) {
            epic.setStatus(TaskStatus.NEW);
        } else if (counter % 2 == 0) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public void add(Task task) {
        task.setId(generateNextId());
        tasks.put(task.getId(), task);
    }

    public void add(Epic epic) {
        epic.setId(generateNextId());
        epics.put(epic.getId(), epic);
    }

    public void add(Subtask subtask) {
        subtask.setId(generateNextId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        epic.addNewSubtaskId(subtask.getId());
    }

    public void update(Task task, int correctId) {
        task.setId(correctId);
        tasks.put(task.getId(), task);
    }

    public void update(Epic epic, int correctId) {
        epic.setId(correctId);
        epics.put(epic.getId(), epic);
        checkStatus(epic);
    }

    public void update(Subtask subtask, int correctId) {
        subtask.setId(correctId);
        subtasks.put(subtask.getId(), subtask);
        checkStatus(epics.get(subtask.getEpicId()));
    }

    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpic(int epicId) {
        return epics.get(epicId);
    }

    public Subtask getSubtask(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    public void removeEpic(int epicId) {
        Epic epic = epics.get(epicId);

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(epicId);
    }

    public void removeSubtask(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        Epic epic = epics.get(subtask.getEpicId());

        (epic.getSubtaskIds()).remove((Integer) subtaskId);
        subtasks.remove(subtaskId);
        checkStatus(epic);
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epics.get(epicId);

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasksList.add(subtasks.get(subtaskId));
        }
        return subtasksList;
    }

    public ArrayList<Task> getAllTasks() {

        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {

        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {

        return new ArrayList<>(subtasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            (epic.getSubtaskIds()).clear();
            checkStatus(epic);
        }
    }

}