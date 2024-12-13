package com.currenjin.wharf.loader;

import com.currenjin.wharf.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceConfigLoaderTest {
    @Test
    void loadTest() {
        String path = "services.yml";

        ServiceConfig actual = ServiceConfigLoader.load(path);

        assertEquals(actual.services().size(), 2);

        assertEquals(actual.services().get(0).name(), "mysql");
        assertEquals(actual.services().get(0).type(), ServiceType.DATABASE);

        assertEquals(actual.services().get(1).name(), "postgresql");
        assertEquals(actual.services().get(1).type(), ServiceType.DATABASE);
    }
}
