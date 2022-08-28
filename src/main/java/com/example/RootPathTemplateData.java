package com.example;

import io.quarkus.qute.TemplateGlobal;
import org.eclipse.microprofile.config.ConfigProvider;

public class RootPathTemplateData {

    public static final String ROOT_PATH = ConfigProvider.getConfig().getValue("quarkus.http.root-path", String.class).trim();

    @TemplateGlobal(name = "rootPath")
    static String rootPath() {
        String value = ROOT_PATH;
        if (!value.startsWith("/")) {
            value = '/' + value;
        }
        if (value.equals("/")) {
            value = "";
        }
        return value;
    }
}
