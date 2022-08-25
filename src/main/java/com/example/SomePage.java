package com.example;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Comparator;

import static java.util.Objects.requireNonNull;

@Path("/")
public class SomePage {

    private final Template page;

    @Inject
    TaskRepository taskRepository;

    public SomePage(Template page) {
        this.page = requireNonNull(page, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        return page
                .data("name", name)
                .data("tasks", taskRepository.getTasks().stream()
                        .sorted(Comparator.comparing(Task::dueDate).thenComparing(Task::title))
                        .toList()
                );
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
