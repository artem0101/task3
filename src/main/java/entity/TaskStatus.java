package entity;

/**
 * Статусы задач.
 */
public enum TaskStatus {

    /**
     * Задача создана, но к её выполнению ещё не приступили.
     */
    NEW,
    /**
     * Над задачей ведётся работа.
     */
    IN_PROGRESS,
    /**
     * Задача выполнена.
     */
    DONE

}
