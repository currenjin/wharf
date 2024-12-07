package com.currenjin.wharf.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

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
    void initCommandGeneratesDockerConfigs(@TempDir Path tempDir) throws Exception {
        createSpringBootProject(tempDir);
        CommandLineRunner runner = new CommandLineRunner();
        String[] args = {"init", tempDir.toString()};

        String output = runner.run(args);

        assertThat(output).contains(CommandLineMessage.SUCCEEDED_GENERATE_TO_DOCKER_CONFIGURATION);
        assertThat(tempDir.resolve("docker-compose.yml")).exists();
        assertThat(tempDir.resolve("Dockerfile")).exists();
    }

    @Test
    void failedInitCommandGeneratesDockerConfigs(@TempDir Path tempDir) {
        CommandLineRunner runner = new CommandLineRunner();
        String[] args = {"init", tempDir.toString()};

        String output = runner.run(args);

        assertThat(output).contains(CommandLineMessage.FAILED_GENERATE_DOCKER_CONFIGURATION);
    }

    @Test
    void invalidCommandGeneratesDockerConfigs(@TempDir Path tempDir) {
        CommandLineRunner runner = new CommandLineRunner();
        String[] args = {"invalid", tempDir.toString()};

        String output = runner.run(args);

        assertThat(output).contains(CommandLineMessage.INVALID_COMMAND_LINE);
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
