package repositories;

import java.util.*;

import entity.Task;


public class TaskRepo {

    private final Map<Integer, Task> taskMap = new HashMap<>();

    public List<Task> getTaskLst() {
        return new ArrayList<>(taskMap.values());
    }

    public void clearTaskMap() {
        taskMap.clear();
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public Optional<Task> getTaskById(Integer id) {
        return Optional.ofNullable(taskMap.get(id));
    }

    public void removeTask(Integer id) {
        taskMap.remove(id);
    }

    public void updateTask(Task task) {
        taskMap.replace(task.getId(), task);
    }

}
