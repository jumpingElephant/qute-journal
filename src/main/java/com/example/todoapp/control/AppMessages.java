package com.example.todoapp.control;

import io.quarkus.qute.i18n.Message;
import io.quarkus.qute.i18n.MessageBundle;

@MessageBundle
public interface AppMessages {

    @Message(value = "<task_list_caption>")
    String task_list_caption();

    @Message("Hello, {name}!")
    String hello_name(String name);

    @Message("{name} is {#when age} {#is 0}not even one year {#is 1}one year{#else}{age} years{/when} old")
    String hello_name_age(String name, Integer age);
}