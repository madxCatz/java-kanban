public class Main {

    public static void main(String[] args) {
        System.out.println("Start!");

        TaskManager manager = Managers.getDefault();

        Task firstProject = new Task("Первый проект", "Написать код для конструктора обедов",
                TaskStatus.NEW);
        Task secondProject = new Task("Второй проект", "Создать приложение трекер-задач",
                TaskStatus.NEW);
        manager.add(firstProject);
        manager.add(secondProject);

        Epic removal = new Epic("Переезд", "Подготовка к переезду", TaskStatus.NEW);
        manager.add(removal);
        Subtask stageOne = new Subtask("Собрать вещи", "Собрать в сумки все вещи в квартире",
                TaskStatus.NEW, removal.getId());
        Subtask stageTwo = new Subtask("Заказать машину", "Воспользоваться сервисом перевозок " +
                "для вызова грузовой машины", TaskStatus.NEW, removal.getId());
        manager.add(stageOne);
        manager.add(stageTwo);

        Epic startWorking = new Epic("Начать рабочий день", "Приготовиться к рабочему дню",
                TaskStatus.NEW);
        manager.add(startWorking);
        Subtask startPoint = new Subtask("Включить компьютер", "Нажать кнопку ПУСК",
                TaskStatus.NEW, startWorking.getId());
        manager.add(startPoint);

        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

}