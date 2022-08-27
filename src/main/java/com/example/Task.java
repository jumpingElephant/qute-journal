package com.example;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record Task(String id, @NotNull @NotEmpty String title, LocalDate dueDate) {
}
