package com.example.todoapp.boundary;

import com.example.todoapp.control.TaskService;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Log
@Path("dev")
public class DevelopmentEndpoint {

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
    TaskService taskService;

    @GET
    @Path("reset")
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    public Response resetTasks() {
        taskService.init();
        return Response.seeOther(URI.create("/")).build();
    }
}
