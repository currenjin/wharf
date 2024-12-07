package com.currenjin.wharf.domain;

public interface Service {
    String name();

    String version();

    ServiceType getType();
}
