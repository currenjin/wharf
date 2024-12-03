package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Project;
import com.currenjin.wharf.domain.UnsupportedFrameworkException;

public class DockerfileGenerator {
	public Dockerfile generate(Project project) {
		return switch (project.getFramework()) {
			case SPRING_BOOT -> generateSpringBootDockerfile();
			case NODE_JS -> generateNodeJsDockerfile();
			default -> throw new UnsupportedFrameworkException(project.getFramework());
		};
	}

	private Dockerfile generateSpringBootDockerfile() {
		Dockerfile dockerfile = new Dockerfile();
		dockerfile.addLine("FROM openjdk:17-jdk-slim");
		dockerfile.addLine("WORKDIR /app");
		dockerfile.addLine("COPY build/libs/*.jar app.jar");
		dockerfile.addLine("ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]");
		return dockerfile;
	}

	private Dockerfile generateNodeJsDockerfile() {
		Dockerfile dockerfile = new Dockerfile();
		dockerfile.addLine("FROM node:18-alpine");
		dockerfile.addLine("WORKDIR /app");
		dockerfile.addLine("COPY package*.json ./");
		dockerfile.addLine("RUN npm install");
		dockerfile.addLine("COPY . .");
		dockerfile.addLine("EXPOSE 3000");
		dockerfile.addLine("CMD [\"npm\", \"start\"]");
		return dockerfile;
	}
}
