package io.temporal.init.genconfig;

import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.temporal.init.contributors.CopyDirectoryProjectContributor;
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

    @Bean
    ProjectContributor addDockerComposeProjectContributor() {
        return new CopyFileProjectContributor("templates/docker-compose.yml", "docker-compose.yml");
    }

    @Bean
    ProjectContributor addExtraDeploymentProjectContributor() {
        return new CopyDirectoryProjectContributor("templates/deployment", "");
    }

    @Bean
    ProjectContributor addSourceProjectContributor() {
        return new CopyDirectoryProjectContributor("templates/temporal", "src/main/java/com/sample/demo");
    }

    @Bean
    ProjectContributor addReadmeProjectContributor() {
        return new CopyFileProjectContributor("templates/README.md", "README.md");
    }
}
