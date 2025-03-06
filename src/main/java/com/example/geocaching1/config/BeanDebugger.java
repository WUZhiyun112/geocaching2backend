package com.example.geocaching1.config;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import jakarta.annotation.PostConstruct;

@Component
public class BeanDebugger {

    private final ApplicationContext applicationContext;

    public BeanDebugger(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void printAllBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            if (beanName.toLowerCase().contains("security") || beanName.toLowerCase().contains("config")) {
                System.out.println("✅ 发现 Bean: " + beanName);
            }
        }
    }
}
