import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubtaskTest {

    public static TaskManager manager;
    public static Epic epic1;
    public static Subtask subtask1;
    public static Subtask subtask2;

    @BeforeAll
    public static void preparingForTheTest() {
        manager = Managers.getDefault();

        epic1 = new Epic("NewEpic_1", "Description NewEpic_1", TaskStatus.NEW);
        manager.add(epic1);

        subtask1 = new Subtask("Subtask_One", "Description Subtask_One",
                TaskStatus.NEW, epic1.getId());
        subtask2 = new Subtask("Subtask_Two", "Description Subtask_Two",
                TaskStatus.NEW, epic1.getId());
        manager.add(subtask1);
        manager.add(subtask2);
    }

    @Test
    public void comparingSubtasksById() {
        assertEquals(subtask1.getEpicId(), subtask2.getEpicId(), "Id эпиков не совпадают.");

        Subtask subtask = manager.getSubtask(2);

        assertEquals(subtask1, subtask, "Подзадачи не совпадают.");
        assertNotEquals(subtask1, subtask2, "Подзадачи не должны совпадать.");
    }

    @Test
    public void checkingImmutabilityOfSubtaskWhenAdding() {
        Task subtask = manager.getSubtask(subtask1.getId());

        assertEquals("Subtask_One", subtask.getTaskName(), "Имя не совпадает.");
        assertEquals("Description Subtask_One", subtask.getDescription(), "Описание не совпадает.");
        assertEquals(TaskStatus.NEW, subtask.getStatus(), "Неправильный статус задачи.");
        assertEquals(2, subtask.getId(), "id не совпадают.");
    }
}