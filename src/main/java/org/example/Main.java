package org.example;

import org.example.beans.BatchLauncher;
import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        BatchLauncher launcher = applicationContext.getBean(BatchLauncher.class);
        launcher.launch();
    }
}
