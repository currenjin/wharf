package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class DockerfileGeneratorTest {
    private final DockerfileGenerator generator = new DockerfileGenerator();

    @Test
    void generateForSpringBoot() {
        Project project = new Project(Framework.SPRING_BOOT, new ArrayList<>());

        Dockerfile dockerfile = generator.generate(project);

        assertThat(dockerfile.getContent())
            .contains("FROM openjdk:17-jdk-slim")
            .contains("WORKDIR /app")
            .contains("COPY build/libs/*.jar app.jar")
            .contains("ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]");
    }

    @Test
    void generateForNodeJs() {
        Project project = new Project(Framework.NODE_JS, new ArrayList<>());

        Dockerfile dockerfile = generator.generate(project);

        assertThat(dockerfile.getContent())
            .contains("FROM node:18-alpine")
            .contains("WORKDIR /app")
            .contains("COPY package*.json ./")
            .contains("RUN npm install")
            .contains("COPY . .")
            .contains("EXPOSE 3000")
            .contains("CMD [\"npm\", \"start\"]");
    }
}
