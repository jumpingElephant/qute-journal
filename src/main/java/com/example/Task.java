package com.example;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record Task(String id, String title, LocalDate dueDate) {
}
