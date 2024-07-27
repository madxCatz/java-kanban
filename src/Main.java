public class Main {

    public static void main(String[] args) {
        System.out.println("Start!");

        TaskManager manager = Managers.getDefault();
        HistoryManager history = Managers.getDefaultHistory();

        Task firstProject = new Task("Первый проект", "Написать код для конструктора обедов",
                TaskStatus.NEW);
        Task secondProject = new Task("Второй проект", "Создать приложение трекер-задач",
                TaskStatus.NEW);
        manager.add(firstProject);
        manager.add(secondProject);

        Epic removal = new Epic("Переезд", "Подготовка к переезду", TaskStatus.NEW);
        manager.add(removal);
        Subtask stageOne = new Subtask("Арендовать квартиру.", "Найти подходящую квартиру " +
                "и договориться о проживании", TaskStatus.NEW, removal.getId());
        Subtask stageTwo = new Subtask("Собрать вещи", "Собрать в сумки все вещи в квартире",
                TaskStatus.NEW, removal.getId());
        Subtask stageThree = new Subtask("Заказать машину", "Воспользоваться сервисом " +
                "грузоперевозок", TaskStatus.NEW, removal.getId());
        manager.add(stageOne);
        manager.add(stageTwo);
        manager.add(stageThree);

        Epic startWorking = new Epic("Начать рабочий день", "Приготовиться к рабочему дню",
                TaskStatus.NEW);
        manager.add(startWorking);

        printAllTasks(manager);
        System.out.println();

        printHistory(history);
        System.out.println("-------------------------------------------");
        manager.removeTask(firstProject.getId());
        printHistory(history);
        System.out.println("-------------------------------------------");
        manager.removeEpic(removal.getId());
        printHistory(history);
    }

    public static void printAllTasks(TaskManager manager) {
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
    }

    public static void printHistory(HistoryManager history) {
        System.out.println("История:");
        for (Task task : history.getHistory()) {
            System.out.println(task);
        }
    }

}