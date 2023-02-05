package service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collections;

import entity.Epic;
import entity.SubTask;
import entity.Task;

import repositories.TaskRepo;

/**
 * Менеджер управления задачами.
 */
public class TaskService {

    private TaskRepo taskRepo = new TaskRepo();

    /**
     * Получение списка всех хранящихся задач.
     *
     * @return Возвращает список всех созданных задач.
     */
    public List<Task> getAllTask() {
        return taskRepo.getTaskLst();
    }

    /**
     * Производит удаление задач.
     */
    public void removeAllTasks() {
        taskRepo.clearTaskMap();
    }

    /**
     * Создаёт простую задачу.
     *
     * @param name        Наименование задачи.
     * @param description Описание задачи.
     */
    public void createTask(String name, String description) {
        var newTask = new Task(name, description);
        taskRepo.addTask(newTask);
    }

    /**
     * Создаёт эпик.
     *
     * @param name        Наименование задачи.
     * @param description Описание задачи.
     */
    public void createEpic(String name, String description) {
        var newTask = new Epic(name, description);
        taskRepo.addTask(newTask);
    }

    /**
     * Создаёт  подзадачу.
     *
     * @param name        Наименование задачи.
     * @param description Описание задачи.
     * @param epicId      Идентификатор эпика, к которому будет прикреплена подзадача.
     */
    public void createSubtask(String name, String description, Integer epicId) {
        var targetEpic = getTaskById(epicId);
        /*
         * Так как подзадача не может существовать отдельно от эпика - предварительно необходимо найти задачу с типом 'Эпик'.
         * Если эпика с нужным идентификатором не было создано - подзадача не создастся.
         */
        if (targetEpic.isEmpty()) {
            System.out.println("Эпик с номером " + epicId + " не существует.");
        } else {
            var epic = (Epic) targetEpic.get();
            var subTask = epic.addSubTask(name, description);
            taskRepo.addTask(subTask);
        }
    }

    /**
     * Предоставление задачи по её идентификатору.
     *
     * @param id Идентификатор задачи.
     * @return Возвращает задачу по её идентификатору.
     */
    public Optional<Task> getTaskById(Integer id) {
        return taskRepo.getTaskById(id);
    }

    /**
     * Удаляет задачу по идентификатору.
     *
     * @param id Идентификатор задачи.
     */
    public void removeById(Integer id) {
        var optionalTask = getTaskById(id);
        /*
         * Для того что бы удалить задачу она должна была быть создана ранее.
         */
        if (optionalTask.isEmpty()) {
            System.out.println("Задача с номером " + id + " не существует.");
        } else {
            var task = optionalTask.get();
            if (task instanceof SubTask) {
                removeSubTask((SubTask) task);
            } else if (task instanceof Epic) {
                removeEpic((Epic) task);
            } else {
                taskRepo.removeTask(task.getId());
            }
        }
    }

    /**
     * Обновляет задачу.
     *
     * @param updateTask
     *        Задача.
     */
    public void updateTask(Task updateTask) {
        if (updateTask instanceof SubTask) {
            updateSubTask((SubTask) updateTask);
        } else if (updateTask instanceof Epic) {
            updateEpic((Epic) updateTask);
        } else {
            taskRepo.updateTask(updateTask);
        }
    }

    /**
     * Предоставляет список подзадач определённого эпика.
     *
     * @param id
     *        Идентификатор эпика.
     *
     * @return Возвращает список подзадач.
     */
    public List<SubTask> getSubTasksOfEpic(Integer id) {
        var optionalTask = getTaskById(id);
        /*
         * Для того что бы удалить задачу она должна была быть создана ранее.
         */
        if (optionalTask.isEmpty()) {
            System.out.println("Задача с номером " + id + " не существует.");
        } else {
            var task = optionalTask.get();
            if (task instanceof Epic) {
                return ((Epic) task).getSubTaskList();
            } else {
                System.out.println("Задача не является эпиком.");
            }
        }
        return Collections.emptyList();
    }

    private void removeSubTask(SubTask subTask) {
        var epic = (Epic) taskRepo.getTaskById(subTask.getEpicId()).get();
        epic.removeSubTask(subTask);
        taskRepo.removeTask(subTask.getId());
    }

    private void removeEpic(Epic epic) {
        var subTaskList = new ArrayList<>(epic.getSubTaskList());
        subTaskList.forEach(this::removeSubTask);
        taskRepo.removeTask(epic.getId());
    }

    private void updateSubTask(SubTask subTask) {
        var epic = (Epic) taskRepo.getTaskById(subTask.getEpicId()).get();
        epic.updateSubTask(subTask);
        updateEpic(epic);
        taskRepo.updateTask(subTask);
    }

    private void updateEpic(Epic epic) {
        epic.updateTask(epic);
        updateTask(epic);
    }

}
