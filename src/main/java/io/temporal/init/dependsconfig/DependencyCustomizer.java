package io.temporal.init.dependsconfig;

import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.stereotype.Component;

@Component
public class DependencyCustomizer implements BuildCustomizer<MavenBuild> {
    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public void customize(MavenBuild build) {
        build.dependencies().add("opentelemetry-exporter-otlp", "io.opentelemetry",
                "opentelemetry-exporter-otlp", DependencyScope.COMPILE);
        build.dependencies().add("opentelemetry-opentracing-shim", "io.opentelemetry",
                "opentelemetry-opentracing-shim", DependencyScope.COMPILE);
        build.dependencies().add("micrometer-tracing-bridge-otel", "io.micrometer",
                "micrometer-tracing-bridge-otel", DependencyScope.COMPILE);
        build.dependencies().add("docker-compose", "org.springframework.boot",
                "spring-boot-docker-compose", DependencyScope.COMPILE);
    }
}
