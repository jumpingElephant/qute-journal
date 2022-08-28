package com.example.todoapp.control;

import com.example.todoapp.entity.Task;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class TaskService {

    public void init() {
        Task.deleteAll();
        Stream.of(
                        Task.builder()
                                .key(UUID.randomUUID().toString())
                                .title("Eat")
                                .dueDate(LocalDate.now())
                                .build(),
                        Task.builder()
                                .key(UUID.randomUUID().toString())
                                .title("Sleep")
                                .dueDate(LocalDate.now())
                                .build())
                .forEach(task -> task.persist());
    }

    public List<Task> getTasks() {
        return Task.listAll();
    }

    public Optional<Task> findTask(String taskId) {
        return Task.findByKey(taskId);
    }

    public Task createTask(Task task) {
        Task persistedTask = Task.builder()
                .key(UUID.randomUUID().toString())
                .title(task.title)
                .dueDate(task.dueDate)
                .build();
        persistedTask.persist();
        return persistedTask;
    }

    public Optional<Task> updateTask(String taskKey, Task task) {
        return Task.findByKey(taskKey)
                .map(taskCopy -> {
                    taskCopy.setTitle(task.getTitle());
                    taskCopy.setDueDate(task.getDueDate());
                    taskCopy.update();
                    return taskCopy;
                });
    }

    public boolean deleteTask(String taskKey) {
        return Task.deleteByKey(taskKey);
    }
}
