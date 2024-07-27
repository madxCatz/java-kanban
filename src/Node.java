public class Node {

    protected Task task;
    protected Node next;
    protected Node prev;

    public Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }
}