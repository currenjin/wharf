package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RedisServiceDetectorTest {

    private final ServiceDetector detector = new RedisServiceDetector();

    @Test
    void detectRedisFromGradle(@TempDir Path tempDir) throws Exception {
        createGradleWithRedisDependency(tempDir);

        Service service = detector.detect(tempDir);

        assertThat(service)
            .satisfies(redis -> {
                assertThat(redis.name()).isEqualTo("redis");
                assertThat(redis.version()).isEqualTo("7.0");
                assertThat(redis.getType()).isEqualTo(ServiceType.CACHE);
            });
    }

    @Test
    void returnEmptyListWhenNoBuildGradle(@TempDir Path tempDir) {
        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    @Test
    void returnEmptyListWhenBuildGradleIsEmpty(@TempDir Path tempDir) throws Exception {
        Files.writeString(tempDir.resolve("build.gradle"), "");

        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    @Test
    void returnEmptyListWhenIOException(@TempDir Path tempDir) throws Exception {
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, "content");
        Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());

        Service service = detector.detect(tempDir);

        assertThat(service).isNull();
    }

    private void createGradleWithRedisDependency(Path directory) throws Exception {
        String buildGradle = """
            dependencies {
                implementation 'org.springframework.boot:spring-boot-starter-data-redis'
            }
            """;
        Files.writeString(directory.resolve("build.gradle"), buildGradle);
    }
}
