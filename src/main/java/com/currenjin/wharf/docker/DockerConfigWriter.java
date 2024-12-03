package com.currenjin.wharf.docker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DockerConfigWriter {
	private final DockerComposeYamlGenerator composeGenerator;

	public DockerConfigWriter() {
		this.composeGenerator = new DockerComposeYamlGenerator();
	}

	public void write(Path directory, DockerCompose compose, Dockerfile dockerfile) throws IOException {
		writeDockerCompose(directory, compose);
		writeDockerfile(directory, dockerfile);
	}

	private void writeDockerCompose(Path directory, DockerCompose compose) throws IOException {
		Path dockerComposePath = directory.resolve("docker-compose.yml");
		String content = composeGenerator.generate(compose);
		Files.writeString(dockerComposePath, content);
	}

	private void writeDockerfile(Path directory, Dockerfile dockerfile) throws IOException {
		Path dockerfilePath = directory.resolve("Dockerfile");
		Files.writeString(dockerfilePath, dockerfile.getContent());
	}
}
