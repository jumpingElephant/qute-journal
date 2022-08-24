package com.example;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Task(String title, LocalDate dueDate) {
}
