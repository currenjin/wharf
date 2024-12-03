package com.currenjin.wharf.cli;

import java.nio.file.Path;
import java.util.List;

import com.currenjin.wharf.analyzer.DefaultProjectAnalyzer;
import com.currenjin.wharf.detector.GradleFrameworkDetector;
import com.currenjin.wharf.detector.NodeFrameworkDetector;
import com.currenjin.wharf.docker.DockerComposeGenerator;
import com.currenjin.wharf.docker.DockerConfigWriter;
import com.currenjin.wharf.docker.DockerfileGenerator;

public class CommandLineRunner {

	private final DefaultProjectAnalyzer analyzer;
	private final DockerComposeGenerator composeGenerator;
	private final DockerfileGenerator dockerfileGenerator;
	private final DockerConfigWriter configWriter;

	public CommandLineRunner() {
		this.analyzer = new DefaultProjectAnalyzer(
			List.of(new GradleFrameworkDetector(), new NodeFrameworkDetector())
		);
		this.composeGenerator = new DockerComposeGenerator();
		this.dockerfileGenerator = new DockerfileGenerator();
		this.configWriter = new DockerConfigWriter();
	}

	public String run(String[] args) {
		if (args.length < 1) {
			return """
                   Usage:
                   wharf init [path]     Initialize Docker configuration
                   """;
		}

		String command = args[0];
		String targetPath = args.length > 1 ? args[1] : ".";

		if ("init".equals(command)) {
			return initializeDocker(Path.of(targetPath));
		}

		return "";
	}

	private String initializeDocker(Path projectPath) {
		try {
			var project = analyzer.analyze(projectPath);
			var compose = composeGenerator.generate(project);
			var dockerfile = dockerfileGenerator.generate(project);
			configWriter.write(projectPath, compose, dockerfile);

			return "Docker configuration generated successfully";
		} catch (Exception e) {
			return "Failed to generate Docker configuration: " + e.getMessage();
		}
	}
}
