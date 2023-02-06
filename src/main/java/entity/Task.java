package entity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Сущность задачи.
 */
public class Task {

    /*
     * Счётчик идентификатра, обеспечивающий уникальность.
     */
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    /*
     * Идентификатор задачи.
     */
    protected Integer id;

    /*
     * Наименование задачи.
     */
    protected String name;

    /*
     * Описание задачи.
     */
    protected String description;

    /*
     * Статус задачи.
     */
    protected TaskStatus status;

    public Task(String name, String description) {
        this.id = idCounter.addAndGet(1);
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void updateTask(Task task) {
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

}
