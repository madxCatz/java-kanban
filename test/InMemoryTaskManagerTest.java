import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryTaskManagerTest {

    public static InMemoryTaskManager manager;
    public static Task task1;
    public static Epic epic1;
    public static Subtask subtask1;

    @BeforeAll
    public static void preparingForTheTest() {
        manager = new InMemoryTaskManager();

        task1 = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        manager.add(task1);
        epic1 = new Epic("Epic_One", "Description Epic_One", TaskStatus.NEW);
        manager.add(epic1);
        subtask1 = new Subtask("Subtask_One", "Description Subtask_One",
                TaskStatus.NEW, epic1.getId());
        manager.add(subtask1);
    }

    @Test
    public void checkingForItemsInHashMaps() {
        List<Task> taskList = manager.getAllTasks();
        List<Epic> epicList = manager.getAllEpics();
        List<Subtask> subtaskList = manager.getAllSubtasks();

        assertFalse(taskList.isEmpty(), "Список задач не пустой.");
        assertFalse(epicList.isEmpty(), "Список эпиков не пустой.");
        assertFalse(subtaskList.isEmpty(), "Список подзадач не пустой.");

        assertEquals(task1, taskList.getFirst(), "Задачи не совпадают.");
        assertEquals(epic1, epicList.getFirst(), "Эпики не совпадают.");
        assertEquals(subtask1, subtaskList.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    public void checkingOfGettingTasks() {
        Task task = manager.getTask(task1.getId());
        assertEquals("Task_One", task.getTaskName(), "Названия задач не совпадают.");

        Subtask subtask = manager.getSubtask(subtask1.getId());
        assertEquals("Subtask_One", subtask.getTaskName(), "Названия подзадач не совпадают");

        Epic epic = manager.getEpic(epic1.getId());
        assertEquals("Epic_One", epic.getTaskName(), "Названия эпиков не совпадают.");
    }

    @Test
    public void checkingDeletionOfTask() {
        assertEquals(1, (manager.getAllTasks()).size(), "Неправильное количество задач.");

        Task task2 = new Task("Task_Two", "Description Task_Two", TaskStatus.NEW);
        manager.add(task2);
        assertEquals(2, (manager.getAllTasks()).size(), "Неправильное количество задач.");

        manager.removeTask(task2.getId());
        assertEquals(1, (manager.getAllTasks()).size(), "Неправильное количество задач.");
    }

    @Test
    public void checkingSaveHistoryPreviousVersionOfTask() {
        HistoryManager history = Managers.getDefaultHistory();
        history.getHistory().clear();

        assertTrue(history.getHistory().isEmpty(), "Список должен быть пуст.");

        Task task2 = new Task("Task_Two", "Description Task_Two",
                TaskStatus.NEW);
        manager.add(task2);
        System.out.println(manager.getTask(task2.getId()));

        String name = "Task_Two";
        TaskStatus status = TaskStatus.NEW;
        Task task = history.getHistory().getFirst();

        assertEquals(1, history.getHistory().size(), "Неправильное количество просмотров.");
        assertEquals(name, task.getTaskName(), "Название не совпадает");
        assertEquals(status, task.getStatus(), "Статус не совпадает.");

        manager.update(new Task("Update_Task_Two", "Description Task_Two",
                TaskStatus.IN_PROGRESS), task2.getId());
        System.out.println(manager.getTask(task2.getId()));

        assertEquals(2, history.getHistory().size(), "Неправильное количество просмотров.");
        assertEquals(name, task.getTaskName(), "Название не совпадает");
        assertEquals(status, task.getStatus(), "Статус не совпадает.");

        name = "Update_Task_Two";
        status = TaskStatus.IN_PROGRESS;
        task = history.getHistory().getLast();

        assertEquals(name, task.getTaskName(), "Название не совпадает");
        assertEquals(status, task.getStatus(), "Статус не совпадает.");

        manager.removeTask(task2.getId());

        assertEquals(2, history.getHistory().size(), "Неправильное количество просмотров.");
    }
}