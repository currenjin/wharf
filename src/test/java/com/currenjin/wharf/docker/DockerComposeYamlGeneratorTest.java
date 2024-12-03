package com.currenjin.wharf.docker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DockerComposeYamlGeneratorTest {

	private final DockerComposeYamlGenerator generator = new DockerComposeYamlGenerator();

	@Test
	void generateYaml() {
		DockerCompose compose = new DockerCompose();
		DockerService service = new DockerService("openjdk:17-jdk-slim");
		service.addPort("8080:8080");
		service.addEnvironment("SPRING_PROFILES_ACTIVE=dev");
		service.addVolume("./:/app");
		compose.addService("app", service);

		String yaml = generator.generate(compose);

		String expected = """
                version: '3.8'
                services:
                  app:
                    image: openjdk:17-jdk-slim
                    ports:
                      - 8080:8080
                    environment:
                      - SPRING_PROFILES_ACTIVE=dev
                    volumes:
                      - ./:/app
                """;

		assertThat(yaml).isEqualToIgnoringWhitespace(expected);
	}
}
