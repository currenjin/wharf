package com.currenjin.wharf.domain;

import java.util.Collections;
import java.util.List;

public record Project(
    Framework framework,
    List<Service> requiredServices
) {
    @Override
    public List<Service> requiredServices() {
        return Collections.unmodifiableList(requiredServices);
    }

    public boolean isSupportedFramework() {
        return this.framework.isSupported();
    }
}
