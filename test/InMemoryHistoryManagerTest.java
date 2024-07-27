import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    public void checkingForMissingItemsInHistory() {
        assertTrue(historyManager.getHistory().isEmpty(), "Список не пустой.");
    }

    @Test
    public void checkingAddAndDeleteOneTaskInHistory() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", TaskStatus.NEW);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "В истории должна быть только одна задача.");
        assertEquals(task1, history.getFirst(), "Задачи не совпадают.");

        historyManager.add(task1);
        assertEquals(1, history.size(), "В истории должна быть только одна задача.");
        assertEquals(task1, history.getFirst(), "Задачи не совпадают.");

        historyManager.remove(task1.getId());
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    public void checkingAddAndDeleteTasksInHistory() {
        Task task1 = new Task("Задача 1", "Описание задачи 1", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("Задача 2", "Описание задачи 2", TaskStatus.NEW);
        task2.setId(2);
        Task task3 = new Task("Задача 3", "Описание задачи 3", TaskStatus.NEW);
        task3.setId(3);

        assertTrue(historyManager.getHistory().isEmpty());

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "В истории должно быть три задачи.");
        assertEquals(task3, history.getFirst(), "Задачи не совпадают.");
        assertEquals(task2, history.get(1), "Задачи не совпадают.");
        assertEquals(task1, history.getLast(), "Задачи не совпадают.");

        historyManager.remove(task3.getId());
        history = historyManager.getHistory();
        assertEquals(2, history.size(), "В истории должно быть две задачи.");
        assertEquals(task2, history.getFirst(), "Задачи не совпадают.");

        historyManager.add(task3);
        history = historyManager.getHistory();
        assertEquals(3, history.size(), "В истории должно быть три задачи.");
        assertEquals(task3, history.getFirst(), "Задачи не совпадают.");

        historyManager.remove(task1.getId());
        assertEquals(2, historyManager.getHistory().size(), "В истории должно быть две задачи.");

        historyManager.add(task1);
        history = historyManager.getHistory();
        assertEquals(3, history.size(), "В истории должно быть три задачи.");
        assertEquals(task1, history.getFirst(), "Задачи не совпадают.");
        assertEquals(task3, history.get(1), "Задачи не совпадают.");
        assertEquals(task2, history.getLast(), "Задачи не совпадают.");

        historyManager.remove(task3.getId());
        assertEquals(2, historyManager.getHistory().size(), "В истории должно быть две задачи.");

        historyManager.remove(task2.getId());
        history = historyManager.getHistory();
        assertEquals(1, history.size(), "В истории должна быть только одна задача.");
        assertEquals(task1, history.getFirst(), "Задачи не совпадают.");

        historyManager.remove(task1.getId());
        assertTrue(historyManager.getHistory().isEmpty(), "Список не пустой.");
    }
}