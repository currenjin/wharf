package com.currenjin.wharf.domain;

import java.util.Collections;
import java.util.List;

public class Project {
	private final Framework framework;
	private final List<Service> requiredServices;

	public Project(Framework framework, List<Service> requiredServices) {
		this.framework = framework;
		this.requiredServices = requiredServices;
	}

	public Framework getFramework() {
		return framework;
	}

	public List<Service> getRequiredServices() {
		return Collections.unmodifiableList(requiredServices);
	}
}
