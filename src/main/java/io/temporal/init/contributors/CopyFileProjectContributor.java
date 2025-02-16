package io.temporal.init.contributors;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;

@RequiredArgsConstructor
public class CopyFileProjectContributor implements ProjectContributor {
    private final String fromPath;
    private final String toPath;

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void contribute(Path projectRoot) {
        try {
            final var fromClassPath = Path.of(ClassLoader.getSystemResource(fromPath).toURI());
            final var toFinalPath = projectRoot.resolve(toPath);
            FileUtils.copyFile(fromClassPath.toFile(), toFinalPath.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
