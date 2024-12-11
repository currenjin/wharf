package com.currenjin.wharf;

import com.currenjin.wharf.analyzer.DefaultProjectAnalyzer;
import com.currenjin.wharf.detector.*;
import com.currenjin.wharf.docker.DockerComposeGenerator;
import com.currenjin.wharf.docker.DockerConfigWriter;
import com.currenjin.wharf.docker.DockerfileGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectSetupIntegrationTest {
    List<FrameworkDetector> frameworkDetectorList = List.of(
        new SpringBootFrameworkDetector(),
        new NodeFrameworkDetector());

    List<ServiceDetector> serviceDetectorList = List.of(
        new MySQLServiceDetector(),
        new PostgreSQLServiceDetector(),
        new RabbitMQServiceDetector(),
        new RedisServiceDetector());

    @TempDir
    Path tempDir;

    @Test
    void setupSpringBootProject() throws Exception {
        createSpringBootProject(tempDir);

        DefaultProjectAnalyzer analyzer = new DefaultProjectAnalyzer(
            frameworkDetectorList, serviceDetectorList
        );

        DockerComposeGenerator composeGenerator = new DockerComposeGenerator();
        DockerfileGenerator dockerfileGenerator = new DockerfileGenerator();
        DockerConfigWriter configWriter = new DockerConfigWriter();

        Path projectPath = tempDir.resolve("spring-project");
        var project = analyzer.analyze(projectPath);
        var compose = composeGenerator.generate(project);
        var dockerfile = dockerfileGenerator.generate(project);
        configWriter.write(projectPath, compose, dockerfile);

        Path dockerComposePath = projectPath.resolve("docker-compose.yml");
        Path dockerfilePath = projectPath.resolve("Dockerfile");

        assertThat(dockerComposePath).exists();
        assertThat(dockerfilePath).exists();

        String composeContent = Files.readString(dockerComposePath);
        assertThat(composeContent)
            .contains("version: '3.8'")
            .contains("build: .")
            .contains("8080:8080")
            .contains("SPRING_PROFILES_ACTIVE=dev");

        String dockerfileContent = Files.readString(dockerfilePath);
        assertThat(dockerfileContent)
            .contains("FROM openjdk:17-jdk-slim")
            .contains("COPY build/libs/*.jar app.jar");
    }

    private void createSpringBootProject(Path tempDir) throws Exception {
        Path projectDir = tempDir.resolve("spring-project");
        Files.createDirectories(projectDir);

        String buildGradle = """
            plugins {
                id 'java'
                id 'org.springframework.boot' version '3.2.1'
            }

            dependencies {
                implementation 'org.springframework.boot:spring-boot-starter'
            }
            """;

        Files.writeString(projectDir.resolve("build.gradle"), buildGradle);
    }
}
