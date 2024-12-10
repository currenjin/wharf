package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class DockerGeneratorIntegrationTest {

    private final DockerComposeGenerator composeGenerator = new DockerComposeGenerator();
    private final DockerfileGenerator dockerfileGenerator = new DockerfileGenerator();
    private final DockerComposeYamlGenerator yamlGenerator = new DockerComposeYamlGenerator();

    @Test
    void generateDockerConfig() {
        Project project = new Project(Framework.SPRING_BOOT, new ArrayList<>());

        DockerCompose compose = composeGenerator.generate(project);
        String composeYaml = yamlGenerator.generate(compose);
        Dockerfile dockerfile = dockerfileGenerator.generate(project);

        assertThat(composeYaml).contains("8080:8080")
            .contains("SPRING_PROFILES_ACTIVE=dev");

        assertThat(dockerfile.getContent()).contains("FROM openjdk:17-jdk-slim")
            .contains("COPY build/libs/*.jar app.jar");
    }
}
