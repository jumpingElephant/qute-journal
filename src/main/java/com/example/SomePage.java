package com.example;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class SomePage {

    @Inject
    @Location("page.qute.html")
    Template page;

    @Inject
    TaskRepository taskRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        return page
                .data("name", name)
                .data("tasks", taskRepository.getTasks());
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
