import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {

    int nextId = 1;
    private final HashMap<Integer, Task> simpleTasks;
    private final HashMap<Integer, Epic> epicList;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        simpleTasks = new HashMap<>();
        epicList = new HashMap<>();
        subtasks = new HashMap<>();
    }

    private int generateNextId() {
        return nextId++;
    }

    public void checkStatus(Epic epic) {
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

    public void add(Task simpleTask) {
        simpleTask.setId(generateNextId());
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    public void add(Epic epic) {
        epic.setId(generateNextId());
        epicList.put(epic.getId(), epic);
    }

    public void add(Subtask subtask) {
        subtask.setId(generateNextId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epicList.get(subtask.getEpicId());
        epic.addNewSubtaskId(subtask.getId());
    }

    public void update(Task simpleTask, int correctId) {
        simpleTask.setId(correctId);
        simpleTasks.put(simpleTask.getId(), simpleTask);
    }

    public void update(Epic epic, int correctId) {
        epic.setId(correctId);
        epicList.put(epic.getId(), epic);
        checkStatus(epic);
    }

    public void update(Subtask subtask, int correctId) {
        subtask.setId(correctId);
        subtasks.put(subtask.getId(), subtask);
        checkStatus(epicList.get(subtask.getEpicId()));
    }

    public Task getSimpleTask(int simpleTaskId) {
        return simpleTasks.get(simpleTaskId);
    }

    public Epic getEpic(int epicId) {
        return epicList.get(epicId);
    }

    public Subtask getSubtask(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public void removeSimpleTask(int simpleTaskId) {
        simpleTasks.remove(simpleTaskId);
    }

    public void removeEpic(int epicId) {
        Epic epic = epicList.get(epicId);

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        epicList.remove(epicId);
    }

    public void removeSubtask(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        Epic epic = epicList.get(subtask.getEpicId());

        (epic.getSubtaskIds()).remove((Integer) subtaskId);
        subtasks.remove(subtaskId);
        checkStatus(epic);
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epicList.get(epicId);

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasksList.add(subtasks.get(subtaskId));
        }
        return subtasksList;
    }

    public ArrayList<Task> getAllSimpleTasks() {

        return new ArrayList<>(simpleTasks.values());
    }

    public ArrayList<Epic> getAllEpics() {

        return new ArrayList<>(epicList.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {

        return new ArrayList<>(subtasks.values());
    }

    public void removeAllSimpleTasks() {
        simpleTasks.clear();
    }

    public void removeAllEpics() {
        epicList.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epicList.values()) {
            (epic.getSubtaskIds()).clear();
            checkStatus(epic);
        }
    }

}