package entity;

/**
 * Сущность подзадачи.
 */
public class SubTask extends Task {

    /*
     * Идентификатор связного эпика.
     */
    private final Integer epicId;

    SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

}
