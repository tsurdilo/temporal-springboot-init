package io.temporal.init.genconfig;

import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.temporal.init.contributors.CopyFileProjectContributor;
import io.temporal.init.contributors.DeleteFileProjectContributor;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class CustomProjectGenerationConfiguration {
    @Bean
    ProjectContributor deleteApplicationPropertiesProjectContributor() {
        return new DeleteFileProjectContributor("src/main/resources/application.properties");
    }
    @Bean
    ProjectContributor addApplicationYamlProjectContributor() {
        return new CopyFileProjectContributor("templates/application.yaml", "src/main/resources/application.yaml");
    }
}
