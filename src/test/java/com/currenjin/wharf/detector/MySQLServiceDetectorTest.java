package com.currenjin.wharf.detector;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.ServiceType;

class MySQLServiceDetectorTest {
	@Test
	void detectMySQLFromGradle(@TempDir Path tempDir) throws Exception {
		createGradleWithMySQLDependency(tempDir);
		ServiceDetector detector = new MySQLServiceDetector();

		List<Service> services = detector.detect(tempDir);

		assertThat(services).hasSize(1);
		assertThat(services.get(0))
			.satisfies(mysql -> {
				assertThat(mysql.getName()).isEqualTo("mysql");
				assertThat(mysql.getVersion()).isEqualTo("8.0");
				assertThat(mysql.getType()).isEqualTo(ServiceType.DATABASE);
			});
	}

	@Test
	void returnEmptyListWhenNoMySQLDependency(@TempDir Path tempDir) throws Exception {
		createGradleWithoutMySQLDependency(tempDir);
		ServiceDetector detector = new MySQLServiceDetector();

		List<Service> services = detector.detect(tempDir);

		assertThat(services).isEmpty();
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
