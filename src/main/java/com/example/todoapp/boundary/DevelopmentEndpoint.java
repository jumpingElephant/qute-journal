package com.example.todoapp.boundary;

import com.example.todoapp.control.TaskService;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.common.util.LocaleHelper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Locale;

@Log
@Path("dev")
public class DevelopmentEndpoint {

    @Inject
    TaskService taskService;

    @GET
    @Path("reset")
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    public Response getResetTasks(@QueryParam("lang") String language, @Context HttpHeaders headers) {
        taskService.init();
        String contentLanguage;
        URI uri = URI.create("/");
        if (language != null) {
            Locale locale = LocaleHelper.extractLocale(language);
            if (!Locale.getDefault().getLanguage().equals(locale.getLanguage())) {
                contentLanguage = locale.getLanguage();
                uri = UriBuilder.fromUri(URI.create("/")).queryParam("lang", contentLanguage).build();
            }
        }
        return Response
                .seeOther(uri)
                .build();
    }

    @POST
    @Path("reset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postResetTasks() {
        taskService.init();
        return Response
                .ok()
                .build();
    }
}
