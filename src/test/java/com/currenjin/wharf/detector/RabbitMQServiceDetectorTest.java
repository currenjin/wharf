package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RabbitMQServiceDetectorTest {

    @Test
    void detectRabbitMQFromGradle(@TempDir Path tempDir) throws Exception {
        createGradleWithRabbitMQDependency(tempDir);
        ServiceDetector detector = new RabbitMQServiceDetector();

        Service service = detector.detect(tempDir);

        assertThat(service)
            .satisfies(rabbitmq -> {
                assertThat(rabbitmq.name()).isEqualTo("rabbitmq");
                assertThat(rabbitmq.version()).isEqualTo("3.12");
                assertThat(rabbitmq.getType()).isEqualTo(ServiceType.MESSAGE_QUEUE);
            });
    }

    @Test
    void returnEmptyListWhenNoBuildGradle(@TempDir Path tempDir) {
        ServiceDetector detector = new RabbitMQServiceDetector();

        Service services = detector.detect(tempDir);

        assertThat(services).isNull();
    }

    @Test
    void returnEmptyListWhenBuildGradleIsEmpty(@TempDir Path tempDir) throws Exception {
        Files.writeString(tempDir.resolve("build.gradle"), "");
        ServiceDetector detector = new RabbitMQServiceDetector();

        Service services = detector.detect(tempDir);

        assertThat(services).isNull();
    }

    @Test
    void returnEmptyListWhenIOException(@TempDir Path tempDir) throws Exception {
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, "content");
        Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());
        ServiceDetector detector = new RabbitMQServiceDetector();

        Service services = detector.detect(tempDir);

        assertThat(services).isNull();
    }

    private void createGradleWithRabbitMQDependency(Path directory) throws Exception {
        String buildGradle = """
            dependencies {
                implementation 'org.springframework.boot:spring-boot-starter-amqp'
            }
            """;
        Files.writeString(directory.resolve("build.gradle"), buildGradle);
    }
}
