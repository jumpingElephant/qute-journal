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
    @Location("page.qute.html")
    Template page;

    @Inject
    TaskRepository taskRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name, @CookieParam("Application-Action") String applicationAction) {
        log.info("SomePage.get: applicationAction = " + applicationAction);
        return page
                .data("name", name)
                .data("tasks", taskRepository.getTasks())
                .data("applicationAction", applicationAction)
                .data("displayMessage", Objects.nonNull(applicationAction) &&
                        switch (applicationAction) {
                            case "taskDeleted" -> true;
                            default -> false;
                        })
                .data("onTaskDeleted", Objects.equals(applicationAction, "taskDeleted"));
    }

    @DELETE
    @Path("tasks/{taskId}")
    public Response deleteTask(@PathParam("taskId") String taskId) {
        return taskRepository.deleteTask(taskId)
                .map(task -> Response.ok(task).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("reset")
    public Response resetTasks() {
        taskRepository.init();
        return Response.seeOther(URI.create("/")).build();
    }
}
