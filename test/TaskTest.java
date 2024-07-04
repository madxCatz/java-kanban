import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    public static TaskManager manager;
    public static Task task1;
    public static Task task2;

    @BeforeAll
    public static void preparingForTheTest() {
        manager = Managers.getDefault();

        task1 = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        task2 = new Task("Task_Two", "Description Task_Two", TaskStatus.NEW);
        manager.add(task1);
        manager.add(task2);
    }

    @Test
    public void receivingAndComparingTasksById() {
        assertNotEquals(task1, task2, "Задачи совпадают.");

        Task task = manager.getTask(task1.getId());
        assertEquals(task1, task, "Задачи не совпадают.");

        task = new Task("Task_One", "Description Task_One", TaskStatus.NEW);
        manager.add(task);

        assertNotEquals(manager.getTask(task1.getId()), manager.getTask(task.getId()),
                "Задачи не должны совпадать.");
    }

    @Test
    public void checkingImmutabilityOfTaskWhenAdding() {
        Task task = manager.getTask(task1.getId());

        assertEquals("Task_One", task.getTaskName(), "Названия задач не совпадают.");
        assertEquals("Description Task_One", task.getDescription(), "Описание не совпадает.");
        assertEquals(TaskStatus.NEW, task.getStatus(), "Неправильный статус задачи.");
        assertEquals(1, task.getId(), "Неправильный Id");
    }
}