public class Managers {

    private static final HistoryManager HISTORY_MANAGER = new InMemoryHistoryManager();

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return HISTORY_MANAGER;
    }
}