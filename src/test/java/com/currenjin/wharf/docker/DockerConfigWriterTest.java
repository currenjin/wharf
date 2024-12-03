package com.currenjin.wharf.docker;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DockerConfigWriterTest {

	@TempDir
	Path tempDir;

	private final DockerConfigWriter writer = new DockerConfigWriter();

	@Test
	void writeDockerConfigs() throws Exception {
		DockerCompose compose = new DockerCompose();
		DockerService service = new DockerService("openjdk:17-jdk-slim");
		service.addPort("8080:8080");
		service.addEnvironment("SPRING_PROFILES_ACTIVE=dev");
		compose.addService("app", service);

		Dockerfile dockerfile = new Dockerfile();
		dockerfile.addLine("FROM openjdk:17-jdk-slim");
		dockerfile.addLine("WORKDIR /app");
		dockerfile.addLine("COPY build/libs/*.jar app.jar");
		dockerfile.addLine("ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]");

		writer.write(tempDir, compose, dockerfile);

		Path dockerComposePath = tempDir.resolve("docker-compose.yml");
		Path dockerfilePath = tempDir.resolve("Dockerfile");

		assertThat(dockerComposePath).exists();
		assertThat(dockerfilePath).exists();

		String dockerComposeContent = Files.readString(dockerComposePath);
		String dockerfileContent = Files.readString(dockerfilePath);

		assertThat(dockerComposeContent).contains("version: '3.8'")
			.contains("openjdk:17-jdk-slim")
			.contains("8080:8080")
			.contains("SPRING_PROFILES_ACTIVE=dev");

		assertThat(dockerfileContent).contains("FROM openjdk:17-jdk-slim")
			.contains("WORKDIR /app")
			.contains("COPY build/libs/*.jar app.jar");
	}
}
