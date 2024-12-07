package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostgreSQLServiceDetectorTest {
    @Test
    void detectPostgreSQLFromGradle(@TempDir Path tempDir) throws Exception {
        createGradleWithPostgreSQLDependency(tempDir);
        ServiceDetector detector = new PostgreSQLServiceDetector();

        List<Service> services = detector.detect(tempDir);

        assertThat(services).hasSize(1);
        assertThat(services.get(0))
            .satisfies(postgresql -> {
                assertThat(postgresql.getName()).isEqualTo("postgresql");
                assertThat(postgresql.getVersion()).isEqualTo("15.0");
                assertThat(postgresql.getType()).isEqualTo(ServiceType.DATABASE);
            });
    }


    @Test
    void returnEmptyListWhenBuildGradleIsEmpty(@TempDir Path tempDir) throws Exception {
        Files.writeString(tempDir.resolve("build.gradle"), "");
        ServiceDetector detector = new PostgreSQLServiceDetector();

        List<Service> services = detector.detect(tempDir);

        assertThat(services).isEmpty();
    }

    @Test
    void returnEmptyListWhenNoBuildGradle(@TempDir Path tempDir) {
        ServiceDetector detector = new PostgreSQLServiceDetector();

        List<Service> services = detector.detect(tempDir);

        assertThat(services).isEmpty();
    }

    @Test
    void returnEmptyListWhenIOException(@TempDir Path tempDir) throws Exception {
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, "content");
        Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());
        ServiceDetector detector = new PostgreSQLServiceDetector();

        List<Service> services = detector.detect(tempDir);

        assertThat(services).isEmpty();
    }

    private void createGradleWithPostgreSQLDependency(Path directory) throws Exception {
        String buildGradle = """
            dependencies {
                implementation 'org.postgresql:postgresql:42.6.0'
            }
            """;
        Files.writeString(directory.resolve("build.gradle"), buildGradle);
    }
}
