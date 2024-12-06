package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Project;

public class DockerComposeGenerator {
	private final DockerServiceGenerator serviceGenerator;

	public DockerComposeGenerator() {
		this.serviceGenerator = new DockerServiceGenerator();
	}

	public DockerCompose generate(Project project) {
		DockerCompose compose = new DockerCompose();

		switch (project.getFramework()) {
			case SPRING_BOOT -> addSpringBootService(compose);
			case NODE_JS -> addNodeJsService(compose);
		}

		project.getRequiredServices().forEach(service -> {
			DockerService dockerService = serviceGenerator.generate(service);
			compose.addService(service.getName(), dockerService);
		});


		return compose;
	}

	private void addSpringBootService(DockerCompose compose) {
		DockerService service = new DockerService("openjdk:17-jdk-slim");
		service.addPort("8080:8080");
		service.addEnvironment("SPRING_PROFILES_ACTIVE=dev");

		compose.addService("app", service);
	}

	private void addNodeJsService(DockerCompose compose) {
		DockerService service = new DockerService("node:18-alpine");
		service.addPort("3000:3000");
		service.addEnvironment("NODE_ENV=development");
		service.addVolume("./:/app");
		service.addVolume("/app/node_modules");

		compose.addService("app", service);
	}
}
