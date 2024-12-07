package com.currenjin.wharf.domain;

public record MessageQueueService(
    String name,
    String version
) implements Service {
    @Override
    public ServiceType getType() {
        return ServiceType.MESSAGE_QUEUE;
    }
}
