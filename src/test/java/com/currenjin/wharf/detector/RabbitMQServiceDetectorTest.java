package com.currenjin.wharf.detector;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.ServiceType;

class RabbitMQServiceDetectorTest {

	@Test
	void detectRabbitMQFromGradle(@TempDir Path tempDir) throws Exception {
		createGradleWithRabbitMQDependency(tempDir);
		ServiceDetector detector = new RabbitMQServiceDetector();

		List<Service> services = detector.detect(tempDir);

		assertThat(services).hasSize(1);
		assertThat(services.getFirst())
			.satisfies(rabbitmq -> {
				assertThat(rabbitmq.getName()).isEqualTo("rabbitmq");
				assertThat(rabbitmq.getVersion()).isEqualTo("3.12");
				assertThat(rabbitmq.getType()).isEqualTo(ServiceType.MESSAGE_QUEUE);
			});
	}

	@Test
	void returnEmptyListWhenNoBuildGradle(@TempDir Path tempDir) {
		ServiceDetector detector = new RabbitMQServiceDetector();

		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
	}

	@Test
	void returnEmptyListWhenBuildGradleIsEmpty(@TempDir Path tempDir) throws Exception {
		Files.writeString(tempDir.resolve("build.gradle"), "");
		ServiceDetector detector = new RabbitMQServiceDetector();

		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
	}

	@Test
	void returnEmptyListWhenIOException(@TempDir Path tempDir) throws Exception {
		Path buildGradlePath = tempDir.resolve("build.gradle");
		Files.writeString(buildGradlePath, "content");
		Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());
		ServiceDetector detector = new RabbitMQServiceDetector();

		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
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