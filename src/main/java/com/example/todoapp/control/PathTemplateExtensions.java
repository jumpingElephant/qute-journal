package com.example.todoapp.control;

import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateExtension.TemplateAttribute;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathTemplateExtensions {

    public static final String ATTR_OVERRIDE_LANGUAGE = "override-language";

    public static final String ROOT_PATH = ConfigProvider.getConfig().getValue("quarkus.http.root-path", String.class).trim();

    @TemplateExtension(namespace = "path")
    static String root() {
        String value = ROOT_PATH;
        if (!value.startsWith("/")) {
            value = '/' + value;
        }
        if (value.equals("/")) {
            value = "";
        }
        return value;
    }

    @TemplateExtension(namespace = "path")
    static String queryParams(@TemplateAttribute(ATTR_OVERRIDE_LANGUAGE) Object language) {
        HashMap<String, String> map = new HashMap<>();
        if (language instanceof String l) {
            map.put("lang", l);
        }
        return map.isEmpty() ? "" : "?%s".formatted(map.entrySet()
                .stream()
                .filter(entry -> Objects.nonNull(entry.getValue()))
                .map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("&")));
    }
}