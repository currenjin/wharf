package com.currenjin.wharf.domain;

public record DatabaseService(
    String name,
    String version
) implements Service {
    @Override
    public ServiceType getType() {
        return ServiceType.DATABASE;
    }
}
