package com.currenjin.wharf.docker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.currenjin.wharf.domain.DatabaseService;
import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;

class DockerComposeGeneratorTest {

	private final DockerComposeGenerator generator = new DockerComposeGenerator();

	@Test
	void generateForSpringBoot() {
		Project project = new Project(Framework.SPRING_BOOT, new ArrayList<>());

		DockerCompose compose = generator.generate(project);

		assertThat(compose.getServices()).hasSize(1);
		DockerService appService = compose.getServices().get("app");
		assertThat(appService.getImage()).isEqualTo("openjdk:17-jdk-slim");
		assertThat(appService.getPorts()).contains("8080:8080");
		assertThat(appService.getEnvironment()).contains("SPRING_PROFILES_ACTIVE=dev");
	}

	@Test
	void generateForNodeJs() {
		Project project = new Project(Framework.NODE_JS, new ArrayList<>());

		DockerCompose compose = generator.generate(project);

		assertThat(compose.getServices()).hasSize(1);
		DockerService appService = compose.getServices().get("app");
		assertThat(appService.getImage()).isEqualTo("node:18-alpine");
		assertThat(appService.getPorts()).contains("3000:3000");
		assertThat(appService.getEnvironment())
			.contains("NODE_ENV=development");
		assertThat(appService.getVolumes())
			.contains("./:/app")
			.contains("/app/node_modules");
	}

	@Test
	void generateWithMySQLService() {
		Project project = new Project(Framework.SPRING_BOOT,
			List.of(new DatabaseService("mysql", "8.0")));
		DockerComposeGenerator generator = new DockerComposeGenerator();

		DockerCompose compose = generator.generate(project);
		String yaml = new DockerComposeYamlGenerator().generate(compose);

		assertThat(yaml)
			.contains("mysql:")
			.contains("image: mysql:8.0")
			.contains("MYSQL_ROOT_PASSWORD")
			.contains("MYSQL_DATABASE")
			.contains("3306:3306");
	}
}
