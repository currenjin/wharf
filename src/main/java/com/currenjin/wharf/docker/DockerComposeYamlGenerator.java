package com.currenjin.wharf.docker;

public class DockerComposeYamlGenerator {
    private static final String INDENT = "  ";

    public String generate(DockerCompose compose) {
        StringBuilder yaml = new StringBuilder();
        yaml.append("version: '3.8'\n");
        yaml.append("services:\n");

        compose.getServices().forEach((name, service) -> {
            yaml.append(INDENT).append(name).append(":\n");
            appendServiceDetails(yaml, service);
        });

        return yaml.toString();
    }

    private void appendServiceDetails(StringBuilder yaml, DockerService service) {
        if (service.getBuild() != null) {
            yaml.append(INDENT).append(INDENT).append("build: ").append(service.getBuild()).append("\n");
        } else if (service.getImage() != null) {
            yaml.append(INDENT).append(INDENT).append("image: ").append(service.getImage()).append("\n");
        }

        if (!service.getPorts().isEmpty()) {
            yaml.append(INDENT).append(INDENT).append("ports:\n");
            service.getPorts().forEach(port ->
                yaml.append(INDENT).append(INDENT).append(INDENT).append("- ").append(port).append("\n")
            );
        }

        if (!service.getEnvironment().isEmpty()) {
            yaml.append(INDENT).append(INDENT).append("environment:\n");
            service.getEnvironment().forEach(env ->
                yaml.append(INDENT).append(INDENT).append(INDENT).append("- ").append(env).append("\n")
            );
        }

        if (!service.getVolumes().isEmpty()) {
            yaml.append(INDENT).append(INDENT).append("volumes:\n");
            service.getVolumes().forEach(volume ->
                yaml.append(INDENT).append(INDENT).append(INDENT).append("- ").append(volume).append("\n")
            );
        }
    }
}
