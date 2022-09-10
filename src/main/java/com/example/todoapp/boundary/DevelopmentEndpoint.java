package com.example.todoapp.boundary;

import com.example.todoapp.control.TaskService;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.common.util.LocaleHelper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Locale;

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
    public Response resetTasks(@QueryParam("lang") String language, @Context HttpHeaders headers) {
        taskService.init();
        String contentLanguage = null;
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
}
