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

class RedisServiceDetectorTest {

	private final ServiceDetector detector = new RedisServiceDetector();

	@Test
	void detectRedisFromGradle(@TempDir Path tempDir) throws Exception {
		createGradleWithRedisDependency(tempDir);

		List<Service> services = detector.detect(tempDir);

		assertThat(services).hasSize(1);
		assertThat(services.get(0))
			.satisfies(redis -> {
				assertThat(redis.getName()).isEqualTo("redis");
				assertThat(redis.getVersion()).isEqualTo("7.0");
				assertThat(redis.getType()).isEqualTo(ServiceType.CACHE);
			});
	}

	@Test
	void returnEmptyListWhenNoBuildGradle(@TempDir Path tempDir) {
		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
	}

	@Test
	void returnEmptyListWhenBuildGradleIsEmpty(@TempDir Path tempDir) throws Exception {
		Files.writeString(tempDir.resolve("build.gradle"), "");

		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
	}

	@Test
	void returnEmptyListWhenIOException(@TempDir Path tempDir) throws Exception {
		Path buildGradlePath = tempDir.resolve("build.gradle");
		Files.writeString(buildGradlePath, "content");
		Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());

		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
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
