package com.example;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Objects;

@Log
@Path("/")
public class SomePage {

    @Inject
    @Location("task.list.html")
    Template page;
    @Inject
    @Location("task.create.html")
    Template createPage;
    @Inject
    @Location("task.edit.html")
    Template editPage;

    @Inject
    TaskRepository taskRepository;

    public static final String KEY_APPLICATION_ACTION = "applicationAction";
    public static final String KEY_DISPLAY_MESSAGE = "displayMessage";

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name, @CookieParam("Application-Action") String applicationAction) {
        log.info("SomePage.get: applicationAction = " + applicationAction);
        return page
                .data("name", name)
                .data("tasks", taskRepository.getTasks())
                .data(KEY_APPLICATION_ACTION, applicationAction)
                .data(KEY_DISPLAY_MESSAGE, Objects.nonNull(applicationAction) &&
                        switch (applicationAction) {
                            case "taskCreated", "taskDeleted" -> true;
                            default -> false;
                        })
                .data("onTaskCreated", Objects.equals(applicationAction, "taskCreated"))
                .data("onTaskDeleted", Objects.equals(applicationAction, "taskDeleted"));
    }

    @GET
    @Path("tasks")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createTaskPage() {
        log.info("SomePage.createTaskPage");
        return createPage
                .data("today", java.time.LocalDate.now())
                .data(KEY_DISPLAY_MESSAGE, false);
    }

    @GET
    @Path("tasks/{taskId}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance editTaskPage(@PathParam("taskId") String taskId) {
        log.info("SomePage.editTask: taskId = " + taskId);
        taskId = taskRepository.findTask(taskId)
                .map(Task::id)
                .orElseGet(() -> taskRepository.findAnyTask().id());
        return editPage
                .data("task", taskRepository.findTask(taskId).orElseThrow(NotFoundException::new))
                .data(KEY_DISPLAY_MESSAGE, false);
    }

    @POST
    @Path("tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(@Valid @NotNull Task task) {
        log.info("SomePage.createTask");
        Task createdTask = taskRepository.createTask(task);
        return Response.created(URI.create("/tasks/%s".formatted(createdTask.id())))
                .entity(createdTask)
                .build();
    }

    @PUT
    @Path("tasks/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("taskId") String taskId, @Valid @NotNull Task task) {
        return taskRepository.updateTask(taskId, task)
                .map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("tasks/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("taskId") String taskId) {
        return taskRepository.deleteTask(taskId)
                .map(task -> Response.ok(task).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("dev/reset")
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    public Response resetTasks() {
        taskRepository.init();
        return Response.seeOther(URI.create("/")).build();
    }
}
