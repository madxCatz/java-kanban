import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    private final TaskManager manager = Managers.getDefault();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    public void checkingInitializationHistoryManager() {
        assertNotNull(historyManager.getHistory(), "История не возвращается.");
        assertTrue(historyManager.getHistory().isEmpty());
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