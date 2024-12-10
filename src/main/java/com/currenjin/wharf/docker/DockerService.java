package com.currenjin.wharf.docker;

import java.util.ArrayList;
import java.util.List;

public class DockerService {
    private String build;
    private final String image;
    private final List<String> ports;
    private final List<String> environment;
    private final List<String> volumes;

    public DockerService(String image) {
        this.build = null;
        this.image = image;
        this.ports = new ArrayList<>();
        this.environment = new ArrayList<>();
        this.volumes = new ArrayList<>();
    }

    public void addPort(String port) {
        ports.add(port);
    }

    public void addEnvironment(String env) {
        environment.add(env);
    }

    public String getImage() {
        return image;
    }

    public List<String> getPorts() {
        return new ArrayList<>(ports);
    }

    public List<String> getEnvironment() {
        return new ArrayList<>(environment);
    }

    public void addVolume(String volume) {
        volumes.add(volume);
    }

    public List<String> getVolumes() {
        return new ArrayList<>(volumes);
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }
}
