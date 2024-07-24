import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    TaskManager manager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    public void checkingInitializationHistoryManager() {
        assertNotNull(historyManager.getHistory(), "История не возвращается.");
    }

    @Test
    public void checkingInitializationTaskManager() {
        Task task = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        manager.add(task);
        List<Task> taskList = manager.getAllTasks();

        assertEquals(1, taskList.size(), "Неверное количество задач.");
        assertEquals(task, taskList.getFirst(), "Задачи не совпадают.");

        manager.removeTask(task.getId());
        taskList = manager.getAllTasks();
        assertTrue(taskList.isEmpty(), "Список не пустой.");
    }
}