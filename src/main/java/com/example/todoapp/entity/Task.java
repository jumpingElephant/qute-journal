package com.example.todoapp.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter()
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(database = "todoapp", collection = "task")
public class Task extends PanacheMongoEntity {
    public String key;
    public String title;
    public LocalDate dueDate;

    public static Optional<Task> findByKey(String key) {
        return find("key", key).firstResultOptional();
    }

    public static boolean deleteByKey(String key) {
        return delete("key", key) == 1;
    }
}
