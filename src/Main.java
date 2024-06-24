public class Main {

    public static void main(String[] args) {
        System.out.println("Start!");

        TaskManager manager = new TaskManager();

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

        System.out.println(manager.getAllTasks());
        System.out.println();
        System.out.println(manager.getAllEpics());
        System.out.println();
        System.out.println(manager.getAllSubtasks());
        System.out.println("-------------------------------------------------------------------------");

        manager.update(new Task("Первый проект", "Написать код для конструктора обедов",
                TaskStatus.IN_PROGRESS), firstProject.getId());
        manager.update(new Task("Второй проект", "Создать приложение трекер-задач",
                TaskStatus.DONE), secondProject.getId());
        manager.update(new Subtask("Собрать вещи", "Собрать в сумки все вещи в квартире",
                TaskStatus.DONE, removal.getId()), stageOne.getId());
        manager.update(new Subtask("Заказать машину", "Воспользоваться сервисом перевозок " +
                "для вызова грузовой машины", TaskStatus.IN_PROGRESS, removal.getId()), stageTwo.getId());
        manager.update(new Subtask("Включить компьютер", "Нажать кнопку ПУСК",
                TaskStatus.DONE, startWorking.getId()), startPoint.getId());

        System.out.println(manager.getAllTasks());
        System.out.println();
        System.out.println(manager.getAllEpics());
        System.out.println();
        System.out.println(manager.getAllSubtasks());
        System.out.println("-------------------------------------------------------------------------");

        manager.removeTask(firstProject.getId());
        manager.removeEpic(startWorking.getId());

        System.out.println(manager.getAllTasks());
        System.out.println();
        System.out.println(manager.getAllEpics());
        System.out.println();
        System.out.println(manager.getAllSubtasks());
    }

}