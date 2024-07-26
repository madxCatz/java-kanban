import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class EpicTest {

    private static TaskManager manager;
    private static Epic epic1;
    private static Epic epic2;
    private static Subtask subtask1;

    @BeforeAll
    public static void preparingForTheTest() {
        manager = Managers.getDefault();

        epic1 = new Epic("Epic_1", "Description Epic_1", TaskStatus.NEW);
        epic2 = new Epic("Epic_2", "Description Epic_2", TaskStatus.NEW);
        manager.add(epic1);
        manager.add(epic2);

        subtask1 = new Subtask("Subtask_1", "Description Subtask_1",
                TaskStatus.NEW, epic1.getId());
        manager.add(subtask1);
    }

    @Test
    public void checkingFullnessOfListSubtasks() {
        List<Integer> subtaskIds = epic1.getSubtaskIds();
        assertEquals(1, subtaskIds.size(), "Неверное количество подзадач.");
        assertEquals(subtask1.getId(), subtaskIds.getFirst(), "Id не совпадают.");

        subtaskIds = epic2.getSubtaskIds();
        assertTrue(subtaskIds.isEmpty(), "Список не пустой.");
    }

    @Test
    public void receivingAndComparingEpics() {
        assertNotEquals(epic1, epic2, "Эпики не должны совпадать.");
        assertNotEquals(epic1.getId(), epic2.getId(), "Id не должны совпадать.");
    }

    @Test
    public void changingStatusOfEpic() {
        assertEquals(TaskStatus.NEW, epic2.getStatus(), "Статус эпика - NEW.");

        Subtask subtask2 = new Subtask("Subtask_2", "Description Subtask_2",
                TaskStatus.NEW, epic2.getId());
        Subtask subtask3 = new Subtask("Subtask_3", "Description Subtask_3",
                TaskStatus.NEW, epic2.getId());
        manager.add(subtask2);
        manager.add(subtask3);
        assertEquals(TaskStatus.NEW, epic2.getStatus(), "Статус эпика - NEW.");
        assertEquals(2, epic2.getSubtaskIds().size(), "Неверное количество подзадач.");

        manager.update(new Subtask("Subtask_2", "Description Subtask_2",
                TaskStatus.IN_PROGRESS, epic2.getId()), subtask2.getId());
        assertEquals(TaskStatus.IN_PROGRESS, epic2.getStatus(), "Статус эпика - IN_PROGRESS.");

        manager.update(new Subtask("Subtask_3", "Description Subtask_3",
                TaskStatus.DONE, epic2.getId()), subtask3.getId());
        assertEquals(TaskStatus.IN_PROGRESS, epic2.getStatus(), "Статус эпика - IN_PROGRESS.");

        manager.update(new Subtask("Subtask_2", "Description Subtask_2",
                TaskStatus.DONE, epic2.getId()), subtask2.getId());
        assertEquals(TaskStatus.DONE, epic2.getStatus(), "Статус эпика - DONE.");

        Subtask subtask4 = new Subtask("Subtask_4", "Description Subtask_4",
                TaskStatus.NEW, epic2.getId());
        manager.add(subtask4);
        assertEquals(3, epic2.getSubtaskIds().size(), "Неверное количество подзадач.");
        assertEquals(TaskStatus.IN_PROGRESS, epic2.getStatus(), "Статус эпика - IN_PROGRESS.");

        manager.removeSubtask(subtask2.getId());
        manager.removeSubtask(subtask3.getId());
        manager.removeSubtask(subtask4.getId());
        assertTrue(epic2.getSubtaskIds().isEmpty(), "Список не пустой.");
        assertEquals(TaskStatus.NEW, epic2.getStatus(), "Статус эпика - NEW.");
    }
}