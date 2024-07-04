import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ManagersTest {

    TaskManager manager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    public void checkingInitializationHistoryManager() {
        List<Task> history = new ArrayList<>();
        assertEquals(history, historyManager.getHistory(), "Списки не совпадают.");

        history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "Список не пустой.");
    }

    @Test
    public void checkingInitializationTaskManager() {
        Task task = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        manager.add(task);

        assertEquals(task, manager.getTask(task.getId()), "Задачи не совпадают.");
        ArrayList<Task> taskList = manager.getAllTasks();

        assertFalse(taskList.isEmpty(), "Список не должен быть пустым");
        assertEquals(task, taskList.getFirst(), "Задачи не совпадают.");
    }
}