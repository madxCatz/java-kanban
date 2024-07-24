import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private Node tail;
    private int size = 0;

    private final Map<Integer, Node> historyMap;

    public InMemoryHistoryManager() {
        historyMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
            size--;
        }
        historyMap.put(task.getId(), linkLast(task));
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
            size--;
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public Node linkLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail != null) {
            oldTail.next = newNode;
        }
        size++;

        return tail;
    }

    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node node = tail;

        for (int i = 1; i <= size; i++) {
            history.add(node.task);
            node = node.prev;
        }
        return history;
    }

    public void removeNode(Node node) {
        Node prevTask = node.prev;
        Node nextTask = node.next;
        if (prevTask != null) {
            prevTask.next = node.next;
        }
        if (nextTask != null) {
            nextTask.prev = node.prev;
        } else {
            tail = prevTask;
        }
    }
}