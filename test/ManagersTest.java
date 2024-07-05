import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    TaskManager manager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @AfterEach
    public void clearingHistory() {
        historyManager.getHistory().clear();
    }

    @Test
    public void checkingInitializationHistoryManager() {
        assertNotNull(historyManager.getHistory(), "История не возвращается.");

        List<Task> history = historyManager.getHistory();

        assertTrue(history.isEmpty(), "Список не пустой.");
    }

    @Test
    public void checkingInitializationTaskManager() {
        Task task = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        manager.add(task);

        assertEquals(task, manager.getTask(task.getId()), "Задачи не совпадают.");
        List<Task> taskList = manager.getAllTasks();

        assertFalse(taskList.isEmpty(), "Список не должен быть пустым");
        assertEquals(task, taskList.getFirst(), "Задачи не совпадают.");
    }
}