package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final List<Task> taskHistory = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (taskHistory.size() == 10) {
            taskHistory.remove(0);
        }
        taskHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }

}
