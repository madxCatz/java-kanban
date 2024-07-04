import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    List<Task> history = historyManager.getHistory();

    @AfterEach
    public void clearingHistory() {
        history.clear();
    }

    @Test
    public void checkingForMissingItemsInHistory() {
        assertTrue(history.isEmpty(), "Список не пустой.");
    }

    @Test
    public void checkingFoAddingNewItemsToHistory() {
        Task task1 = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        historyManager.add(task1);

        assertFalse(history.isEmpty(), "Список пустой.");
        assertEquals(1, history.size(), "Размер списка не равен одному.");

        for (int i = 1; i < 20; i++) {
            historyManager.add(task1);
        }

        assertEquals(10, history.size(), "Размер списка не равен десяти.");
    }
}