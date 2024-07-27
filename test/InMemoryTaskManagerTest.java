import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryTaskManagerTest {

    public static TaskManager manager;
    public static Task task1;
    public static Epic epic1;
    public static Subtask subtask1;

    @BeforeEach
    public void preparingForTheTest() {
        manager = Managers.getDefault();
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
        assertEquals(1, (manager.getAllTasks()).size(), "Неверное количество задач.");

        Task task2 = new Task("Task_Two", "Description Task_Two", TaskStatus.NEW);
        Task task3 = new Task("Task_Three", "Description Task_Three", TaskStatus.NEW);
        manager.add(task2);
        manager.add(task3);
        assertEquals(3, (manager.getAllTasks()).size(), "Неверное количество задач.");

        manager.removeTask(task1.getId());
        assertEquals(2, (manager.getAllTasks()).size(), "Неверное количество задач.");

        manager.removeAllTasks();
        assertTrue(manager.getAllTasks().isEmpty(), "Список не пустой.");
    }

    @Test
    public void checkingDeleteOfEpicSubtasks() {
        Subtask subtask2 = new Subtask("Subtask_Two", "Description Subtask_Two",
                TaskStatus.DONE, epic1.getId());
        manager.add(subtask2);
        assertEquals(2, epic1.getSubtaskIds().size(), "Неверное количество задач.");
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Статус эпика - IN_PROGRESS");

        manager.removeAllSubtasks();
        assertTrue(epic1.getSubtaskIds().isEmpty(), "Список не пустой.");
    }

    @Test
    public void checkingDeleteEpicWithSubtasks() {
        Subtask subtask2 = new Subtask("Subtask_Two", "Description Subtask_Two",
                TaskStatus.DONE, epic1.getId());
        manager.add(subtask2);
        assertEquals(2, epic1.getSubtaskIds().size(), "Неверное количество задач.");
        assertEquals(2, manager.getAllSubtasks().size(), "Неверное количество задач.");

        manager.removeEpic(epic1.getId());
        assertTrue(manager.getAllSubtasks().isEmpty(), "Список не пустой.");
        assertTrue(manager.getAllEpics().isEmpty(), "Список не пустой.");
    }
}