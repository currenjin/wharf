package com.currenjin.wharf.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CommandLineRunnerTest {
	@Test
	void printUsageWhenNoArgs() {
		CommandLineRunner runner = new CommandLineRunner();
		String[] args = {};

		String output = runner.run(args);

		assertThat(output)
			.contains("Usage:")
			.contains("wharf init [path]");
	}

	@Test
	void initCommandPrintsMessage(@TempDir Path tempDir) {
		CommandLineRunner runner = new CommandLineRunner();
		String[] args = {"init", tempDir.toString()};

		String output = runner.run(args);

		assertThat(output).contains("Initializing Docker configuration");
	}

	@Test
	void initCommandGeneratesDockerConfigs(@TempDir Path tempDir) throws Exception {
		createSpringBootProject(tempDir);
		CommandLineRunner runner = new CommandLineRunner();
		String[] args = {"init", tempDir.toString()};

		String output = runner.run(args);

		assertThat(output).contains("Docker configuration generated successfully");
		assertThat(tempDir.resolve("docker-compose.yml")).exists();
		assertThat(tempDir.resolve("Dockerfile")).exists();
	}

	private void createSpringBootProject(Path directory) throws Exception {
		String buildGradle = """
            plugins {
                id 'java'
                id 'org.springframework.boot' version '3.2.1'
            }
            
            dependencies {
                implementation 'org.springframework.boot:spring-boot-starter'
            }
            """;

		Files.writeString(directory.resolve("build.gradle"), buildGradle);
	}
}
