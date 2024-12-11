package com.currenjin.wharf.cli;

import com.currenjin.wharf.analyzer.DefaultProjectAnalyzer;
import com.currenjin.wharf.analyzer.ProjectAnalyzer;
import com.currenjin.wharf.detector.*;
import com.currenjin.wharf.docker.DockerComposeGenerator;
import com.currenjin.wharf.docker.DockerConfigWriter;
import com.currenjin.wharf.docker.DockerfileGenerator;

import java.nio.file.Path;
import java.util.List;

public class CommandLineRunner {
    private final ProjectAnalyzer analyzer;
    private final DockerComposeGenerator composeGenerator;
    private final DockerfileGenerator dockerfileGenerator;
    private final DockerConfigWriter configWriter;

    public CommandLineRunner() {
        List<FrameworkDetector> frameworkDetectorList = List.of(
            new SpringBootFrameworkDetector(),
            new NodeFrameworkDetector());

        List<ServiceDetector> serviceDetectorList = List.of(
            new MySQLServiceDetector(),
            new PostgreSQLServiceDetector(),
            new RabbitMQServiceDetector(),
            new RedisServiceDetector());
        
        this.analyzer = new DefaultProjectAnalyzer(frameworkDetectorList, serviceDetectorList);
        this.composeGenerator = new DockerComposeGenerator();
        this.dockerfileGenerator = new DockerfileGenerator();
        this.configWriter = new DockerConfigWriter();
    }

    public String run(String[] args) {
        if (args.length < 1) {
            return CommandLineMessage.USAGE_INIT_DOCKER_CONFIGURATION;
        }

        String command = args[0];
        String targetPath = args.length > 1 ? args[1] : ".";

        if ("init".equals(command)) {
            return initializeDocker(Path.of(targetPath));
        }

        return CommandLineMessage.INVALID_COMMAND_LINE + command;
    }

    private String initializeDocker(Path projectPath) {
        try {
            var project = analyzer.analyze(projectPath);
            var compose = composeGenerator.generate(project);
            var dockerfile = dockerfileGenerator.generate(project);
            configWriter.write(projectPath, compose, dockerfile);

            return CommandLineMessage.SUCCEEDED_GENERATE_TO_DOCKER_CONFIGURATION;
        } catch (Exception e) {
            return CommandLineMessage.FAILED_GENERATE_DOCKER_CONFIGURATION + e.getMessage();
        }
    }
}
