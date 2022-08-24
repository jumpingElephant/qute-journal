package com.example;

import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskRepository {
    @Getter
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
}
