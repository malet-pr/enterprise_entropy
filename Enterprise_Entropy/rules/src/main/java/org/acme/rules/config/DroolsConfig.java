package org.acme.rules.config;

import org.drools.model.codegen.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";
    Logger log = LoggerFactory.getLogger(DroolsConfig.class);

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        // Load all DRL files from resources/rules folder
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.drl");

        log.info("=== Loading DRL files ===");
        log.info("Found {} DRL files", resources.length );

        for (Resource resource : resources) {
            String filename = resource.getFilename();
            String path = RULES_PATH + filename;
            log.info("Loading: {}", path);
            kieFileSystem.write(ResourceFactory.newClassPathResource(path));
        }

        // Build the kie container with executable domain (no MVEL)
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll(ExecutableModelProject.class);

        // Check for build errors
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            log.error("=== Error building KieContainer ===");
            results.getMessages(Message.Level.ERROR).forEach(msg -> {
                log.error("Message: {}", msg.getText());
                log.error("Path: {}", msg.getPath());
                if (msg.getLine() != -1) {
                    log.error("Line: {}", msg.getLine());
                }
                log.error("---");
            });
            throw new RuntimeException("Error building KieContainer with executable domain");
        }

        // Log successful compilation
        log.info("=== Build Results ===");
        if (results.hasMessages(Message.Level.INFO)) {
            results.getMessages(Message.Level.INFO).forEach(msg ->
                    log.info("INFO: {}", msg.getText()));
        }

        // Get the KieContainer
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        log.info("KieContainer initialized successfully with executable domain");

        return kieContainer;
    }
}