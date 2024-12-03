package com.currenjin.wharf.docker;

import java.util.HashMap;
import java.util.Map;

import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;

public class DockerCompose {
	private final Map<String, DockerService> services;

	public DockerCompose() {
		this.services = new HashMap<>();
	}

	public void addService(String name, DockerService service) {
		services.put(name, service);
	}

	public Map<String, DockerService> getServices() {
		return new HashMap<>(services);
	}
}
