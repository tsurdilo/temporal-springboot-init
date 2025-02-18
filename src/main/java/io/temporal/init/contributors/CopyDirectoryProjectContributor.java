package io.temporal.init.contributors;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;

@RequiredArgsConstructor
public class CopyDirectoryProjectContributor implements ProjectContributor {
    private final String fromDirPath;
    private final String toDirPath;

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public void contribute(Path projectRoot) {
        try {
            final var fromDirClassPath = Path.of(ClassLoader.getSystemResource(fromDirPath).toURI());
            final var toDirFinalPath = projectRoot.resolve(toDirPath);
            FileUtils.copyDirectoryToDirectory(fromDirClassPath.toFile(), toDirFinalPath.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}