package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Project;

public class DockerComposeGenerator {
    private static final String FRAMEWORK_SERVICE_NAME = "app";

    private final DockerServiceGenerator serviceGenerator;

    public DockerComposeGenerator() {
        this.serviceGenerator = new DockerServiceGenerator();
    }

    public DockerCompose generate(Project project) {
        DockerCompose compose = new DockerCompose();

        DockerService frameworkService = serviceGenerator.generate(project.framework());
        compose.addService(FRAMEWORK_SERVICE_NAME, frameworkService);

        project.requiredServices().forEach(service -> {
            DockerService dockerService = serviceGenerator.generate(service);
            compose.addService(service.name(), dockerService);
        });


        return compose;
    }

}
