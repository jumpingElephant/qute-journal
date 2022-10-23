package com.example.todoapp.boundary;

import com.example.todoapp.control.PathTemplateExtensions;
import com.example.todoapp.control.TaskService;
import com.example.todoapp.control.TodoComparators;
import com.example.todoapp.entity.Task;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.i18n.MessageBundles;
import lombok.extern.java.Log;
import org.jboss.resteasy.reactive.common.util.LocaleHelper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

@Log
@Path("/")
public class QutePage {

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

    public static final String KEY_APPLICATION_ACTION = "applicationAction";
    public static final String KEY_DISPLAY_MESSAGE = "displayMessage";

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get(@QueryParam("name") String name, @QueryParam("lang") String language, @Context HttpHeaders headers,
                        @CookieParam("Application-Action") String applicationAction) {
        log.info("QutePage.get: applicationAction='%s', language='%s', name='%s'".formatted(applicationAction, language, name));
        TemplateInstance templateInstance = page
                .data("name", name)
                .data("tasks", taskService.getTasks().stream()
                        .sorted(Comparator
                                .comparing(Task::getDueDate, TodoComparators.NULL_SAFE_COMPARATOR)
                                .thenComparing(Task::getTitle))
                        .toList())
                .data(KEY_APPLICATION_ACTION, applicationAction)
                .data(KEY_DISPLAY_MESSAGE, Objects.nonNull(applicationAction) &&
                        switch (applicationAction) {
                            case "taskCreated", "taskDeleted" -> true;
                            default -> false;
                        })
                .data("onTaskCreated", Objects.equals(applicationAction, "taskCreated"))
                .data("onTaskDeleted", Objects.equals(applicationAction, "taskDeleted"));
        String contentLanguage = null;
        if (language != null) {
            Locale locale = LocaleHelper.extractLocale(language);
            if (!Locale.getDefault().getLanguage().equals(locale.getLanguage())) {
                templateInstance.setAttribute(MessageBundles.ATTRIBUTE_LOCALE, locale)
                        .setAttribute(PathTemplateExtensions.ATTR_OVERRIDE_LANGUAGE, language);
                contentLanguage = locale.getLanguage();
            }
        }
        return Response
                .ok(templateInstance)
                .header(HttpHeaders.CONTENT_LANGUAGE, contentLanguage)
                .build();
    }

    @GET
    @Path("create")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createTaskPage() {
        log.info("QutePage.createTaskPage");
        return createPage
                .data("today", java.time.LocalDate.now())
                .data(KEY_DISPLAY_MESSAGE, false);
    }

    @GET
    @Path("edit/{taskId}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance editTaskPage(@PathParam("taskId") String taskId) {
        log.info("QutePage.editTask: taskId = " + taskId);
        return editPage
                .data("task", taskService.findTask(taskId).orElseThrow(NotFoundException::new))
                .data(KEY_DISPLAY_MESSAGE, false);
    }
}
