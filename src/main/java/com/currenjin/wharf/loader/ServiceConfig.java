package com.currenjin.wharf.loader;

import java.util.ArrayList;
import java.util.List;

public record ServiceConfig(List<ServiceDefinition> services) {
    @Override
    public List<ServiceDefinition> services() {
        return new ArrayList<>(services);
    }
}
