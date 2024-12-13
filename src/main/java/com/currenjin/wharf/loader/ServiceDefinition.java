package com.currenjin.wharf.loader;

import com.currenjin.wharf.domain.ServiceType;

import java.util.List;

public record ServiceDefinition(
    String name,
    ServiceType type,
    String version,
    List<Dependency> dependencies
) {
}
