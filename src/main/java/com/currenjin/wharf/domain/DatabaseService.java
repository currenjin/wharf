package com.currenjin.wharf.domain;

public class DatabaseService implements Service {
	public DatabaseService(String name, String version) {
		this.name = name;
		this.version = version;
	}

	private final String name;
	private final String version;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.DATABASE;
	}
}
