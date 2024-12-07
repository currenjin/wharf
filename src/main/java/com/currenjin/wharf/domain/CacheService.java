package com.currenjin.wharf.domain;

public record CacheService(
    String name,
    String version
) implements Service {
    @Override
    public ServiceType getType() {
        return ServiceType.CACHE;
    }
}
