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

    @Bean
    ProjectContributor addHtmlResources() {
        return new CopyDirectoryProjectContributor("templates/templates", "src/main/resources");
    }

    @Bean
    ProjectContributor addStaticResources() {
        return new CopyDirectoryProjectContributor("templates/js", "src/main/resources/static");
    }

    @Bean
    ProjectContributor deleteDefaultTestProjectContributor() {
        return new DeleteFileProjectContributor("src/test/java/com/sample/demo/DemoApplicationTests.java");
    }

//    @Bean
//    ProjectContributor addWorkflowTestProjectContributor() {
//        return new CopyFileProjectContributor("templates/testing/HelloWorkflowTest.java", "src/test/java/com/sample/demo/HelloWorkflowTest.java");
//    }
//
//    @Bean
//    ProjectContributor addTestAppPropsProjectContributor() {
//        return new CopyFileProjectContributor("templates/testing/application.yaml", "src/test/resources/application.yaml");
//    }
}
