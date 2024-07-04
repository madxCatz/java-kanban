import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class EpicTest {

    public static TaskManager manager;
    public static Epic epic1;
    public static Epic epic2;
    public static Subtask subtask1;

    @BeforeAll
    public static void preparingForTheTest() {
        manager = Managers.getDefault();

        epic1 = new Epic("Epic_1", "Description Epic_1", TaskStatus.NEW);
        epic2 = new Epic("Epic_2", "Description Epic_2", TaskStatus.NEW);
        manager.add(epic1);
        manager.add(epic2);

        subtask1 = new Subtask("Subtask_One", "Description Subtask_One",
                TaskStatus.NEW, epic1.getId());
        manager.add(subtask1);
    }

    @Test
    public void checkingFullnessOfListSubtasks() {
        ArrayList<Integer> subtaskIds = epic1.getSubtaskIds();

        assertEquals(1, subtaskIds.size(), "Неверное количество подзадач.");
        assertEquals(subtask1.getId(), subtaskIds.getFirst(), "Id не совпадают.");

        subtaskIds = epic2.getSubtaskIds();

        assertTrue(subtaskIds.isEmpty(), "Список не пустой.");
    }

    @Test
    public void receivingAndComparingEpicsById() {
        int id = epic1.getId();

        assertNotEquals(id, epic2.getId(), "Эпики не должны совпадать.");

        Epic epic = manager.getEpic(1);

        assertEquals(epic1, epic, "Эпики не совпадают.");
        assertEquals(1, epic1.getId());
        assertEquals(2, epic2.getId());
    }

    @Test
    public void changingStatusOfEpic() {
        assertEquals(TaskStatus.NEW, epic1.getStatus(), "Статус эпика - NEW.");

        manager.update(new Subtask("SubtaskOne", "Description SubtaskOne",
                TaskStatus.DONE, epic1.getId()), subtask1.getId());

        assertEquals(TaskStatus.DONE, epic1.getStatus(), "Статус эпика - DONE.");

        Subtask subtask2 = new Subtask("SubtaskTwo", "Description SubtaskTwo",
                TaskStatus.NEW, epic1.getId());
        manager.add(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "Статус эпика - IN_PROGRESS.");

        manager.update(new Subtask("SubtaskTwo", "Description SubtaskTwo",
                TaskStatus.DONE, epic1.getId()), subtask2.getId());

        assertEquals(TaskStatus.DONE, epic1.getStatus(), "Статус эпика - DONE.");
    }
}