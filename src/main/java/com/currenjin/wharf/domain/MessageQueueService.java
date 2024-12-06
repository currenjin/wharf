package com.currenjin.wharf.domain;

public class MessageQueueService implements Service {
	public MessageQueueService(String name, String version) {
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
		return ServiceType.MESSAGE_QUEUE;
	}
}
