package com.example;

import io.smallrye.mutiny.Uni;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class TaskRepository {
    private Collection<Task> tasks;

    @PostConstruct
    public void init() {
        tasks = new ArrayList<>(List.of(Task.builder()
                        .id(UUID.randomUUID().toString())
                        .title("Eat")
                        .dueDate(LocalDate.now())
                        .build(),
                Task.builder()
                        .id(UUID.randomUUID().toString())
                        .title("Sleep")
                        .dueDate(LocalDate.now())
                        .build()));
    }

    public Optional<Task> deleteTask(String taskId) {
        Set<Task> taskSet = tasks.stream()
                .filter(task -> taskId.equals(task.id()))
                .collect(Collectors.toSet());
        tasks.removeAll(taskSet);
        return taskSet.stream().findFirst();
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
        return tasks;
    }
}
