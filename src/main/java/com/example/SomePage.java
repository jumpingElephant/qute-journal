package com.example;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Objects;

@Log
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SomePage {

    @Inject
    @Location("task.list.html")
    Template page;
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
                            case "taskDeleted" -> true;
                            default -> false;
                        })
                .data("onTaskDeleted", Objects.equals(applicationAction, "taskDeleted"));
    }

    @GET
    @Path("tasks/{taskId}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance editTaskPage(@PathParam("taskId") String taskId) {
        log.info("SomePage.editTask: taskId = " + taskId);
//        taskId = taskRepository.findTask(taskId)
//                .map(Task::id)
//                .orElseGet(() -> taskRepository.findAnyTask().id());
        return editPage
                .data("task", taskRepository.findTask(taskId).orElseThrow(NotFoundException::new))
                .data(KEY_DISPLAY_MESSAGE, false);
    }

    @PUT
    @Path("tasks/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("taskId") String taskId, Task task) {
        return taskRepository.updateTask(taskId, task)
                .map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("tasks/{taskId}")
    public Response deleteTask(@PathParam("taskId") String taskId) {
        return taskRepository.deleteTask(taskId)
                .map(task -> Response.ok(task).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("dev/reset")
    public Response resetTasks() {
        taskRepository.init();
        return Response.seeOther(URI.create("/")).build();
    }
}
