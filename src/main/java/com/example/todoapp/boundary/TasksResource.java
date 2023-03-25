package com.example.todoapp.boundary;

import com.example.todoapp.control.TaskService;
import com.example.todoapp.entity.Task;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Log
@Path("tasks")
public class TasksResource {

    @Inject
    TaskService taskService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        log.info("TasksResource.getTasks");
        return Response.ok(taskService.getTasks()).build();
    }

    @GET
    @Path("{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(@PathParam("taskId") String taskId) {
        log.info("TasksResource.getTasks");
        return taskService.findTask(taskId)
                .map(task -> Response.ok(task).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@Valid @NotNull Task task) {
        log.info("TasksResource.createTask");
        Task createdTask = taskService.createTask(task);
        return Response.created(URI.create("/tasks/%s".formatted(createdTask.key)))
                .entity(createdTask)
                .build();
    }

    @PUT
    @Path("{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("taskId") String taskId, @Valid @NotNull Task task) {
        log.info("TasksResource.updateTask");
        return taskService.updateTask(taskId, task)
                .map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("taskId") String taskId) {
        if (taskService.deleteTask(taskId)) {
            log.info("TasksResource.deleteTask: deleted");
        } else {
            log.info("TasksResource.deleteTask: task not present");
        }
        return Response.ok().build();
    }
}
