package com.example;

import io.smallrye.mutiny.Uni;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@ApplicationScoped
public class TaskRepository {
    private Map<String, Task> tasks;

    @PostConstruct
    public void init() {
        tasks = new HashMap<>(Stream.of(
                        Task.builder()
                                .id(UUID.randomUUID().toString())
                                .title("Eat")
                                .dueDate(LocalDate.now())
                                .build(),
                        Task.builder()
                                .id(UUID.randomUUID().toString())
                                .title("Sleep")
                                .dueDate(LocalDate.now())
                                .build())
                .collect(Collectors.toMap(Task::id, Function.identity())));
    }

    public Task insertTask(Task task) {
        tasks.put(task.id(), task);
        return task;
    }

    public Optional<Task> deleteTask(String taskId) {
        return Optional.ofNullable(tasks.remove(taskId));
    }

    public Uni<List<Task>> getTasks() {
        return Uni.createFrom()
                .item(getTaskCollection().stream()
                        .sorted(Comparator
                                .comparing(Task::dueDate)
                                .thenComparing(Task::title))
                        .toList());
    }

    private Collection<Task> getTaskCollection() {
        return tasks.values();
    }

    public Optional<Task> findTask(String taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    public Optional<Task> updateTask(String taskId, Task task) {
        return findTask(taskId)
                .map(oldTask -> oldTask.toBuilder()
                        .title(task.title())
                        .dueDate(task.dueDate())
                        .build())
                .map(newTask -> {
                    deleteTask(newTask.id());
                    return insertTask(newTask);
                });
    }

    @Deprecated
    public Task findAnyTask() {
        return tasks.values().stream()
                .findFirst()
                .orElseGet(() -> {
                    init();
                    return findAnyTask();
                });
    }
}
