package io.temporal.init.contributors;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class DeleteFileProjectContributor implements ProjectContributor {
    private final String path;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        final var file = projectRoot.resolve(path);
        Files.delete(file);
    }
}
