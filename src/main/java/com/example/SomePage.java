package com.example;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Path("/")
public class SomePage {

    private final Template page;

    public SomePage(Template page) {
        this.page = requireNonNull(page, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        final var tasks = List.of(Task.builder()
                        .title("Eat")
                        .dueDate(LocalDate.now())
                        .build(),
                Task.builder()
                        .title("Sleep")
                        .dueDate(LocalDate.now())
                        .build());
        return page
                .data("name", name)
                .data("tasks", tasks);
    }

}
