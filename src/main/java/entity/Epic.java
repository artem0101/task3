package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сущность эпик.
 */
public class Epic extends Task {

    /*
     * Список подзадач относящихся к эпику.
     */
    private final List<SubTask> subTaskList;

    public Epic(String name, String description) {
        super(name, description);
        subTaskList = new ArrayList<>();
    }

    public SubTask addSubTask(String name, String description) {
        var subTask = new SubTask(name, description, getId());
        this.subTaskList.add(subTask);
        return subTask;
    }

    public List<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void updateSubTask(SubTask subTask) {
        var updatedTask = subTaskList.get(subTaskList.indexOf(subTask));
        updatedTask.updateTask(subTask);
        updateTask(this);
    }

    public void removeSubTask(SubTask subTask) {
        subTaskList.remove(subTask);
        updateTask(this);
    }

    @Override
    public void updateTask(Task task) {
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = getEpicStatus();
    }

    @Override
    public String toString() {
        var subTasksDesc = new StringBuffer();
        subTaskList.forEach(subTask ->
            subTasksDesc.append(subTask.toString())
        );
        return "Epic{" +
                "subTaskList=" + subTasksDesc +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    private TaskStatus getEpicStatus() {
        var subtaskStatuses = subTaskList.stream()
                .map(SubTask::getStatus)
                .collect(Collectors.toSet());
        if (subtaskStatuses.contains(TaskStatus.IN_PROGRESS)) {
            return TaskStatus.IN_PROGRESS;
        } else if (subtaskStatuses.size() == 1 && subtaskStatuses.contains(TaskStatus.DONE)) {
            return TaskStatus.DONE;
        }

        return TaskStatus.NEW;
    }

}
