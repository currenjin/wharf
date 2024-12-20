package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class MySQLServiceDetectorTest {
    @Test
    void detectMySQLFromGradle(@TempDir Path tempDir) throws Exception {
        createGradleWithMySQLDependency(tempDir);
        ServiceDetector detector = new MySQLServiceDetector();

        Service service = detector.detect(tempDir);

        assertThat(service)
            .satisfies(mysql -> {
                assertThat(mysql.name()).isEqualTo("mysql");
                assertThat(mysql.version()).isEqualTo("8.0");
                assertThat(mysql.getType()).isEqualTo(ServiceType.DATABASE);
            });
    }

    @Test
    void returnEmptyListWhenNoMySQLDependency(@TempDir Path tempDir) throws Exception {
        createGradleWithoutMySQLDependency(tempDir);
        ServiceDetector detector = new MySQLServiceDetector();

        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    @Test
    void returnEmptyListWhenNoBuildGradle(@TempDir Path tempDir) {
        ServiceDetector detector = new MySQLServiceDetector();

        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    @Test
    void returnEmptyListWhenBuildGradleIsEmpty(@TempDir Path tempDir) throws Exception {
        Files.writeString(tempDir.resolve("build.gradle"), "");
        ServiceDetector detector = new MySQLServiceDetector();

        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    @Test
    void returnEmptyListWhenIOException(@TempDir Path tempDir) throws Exception {
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, "content");
        Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());
        ServiceDetector detector = new MySQLServiceDetector();

        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    private void createGradleWithMySQLDependency(Path directory) throws Exception {
        String buildGradle = """
            dependencies {
                implementation 'mysql:mysql-connector-java:8.0.28'
            }
            """;
        Files.writeString(directory.resolve("build.gradle"), buildGradle);
    }

    private void createGradleWithoutMySQLDependency(Path directory) throws Exception {
        String buildGradle = """
            dependencies {
                implementation 'org.postgresql:postgresql:42.2.5'
            }
            """;
        Files.writeString(directory.resolve("build.gradle"), buildGradle);
    }
}
