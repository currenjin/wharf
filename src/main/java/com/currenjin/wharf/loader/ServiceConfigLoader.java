package com.currenjin.wharf.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

public class ServiceConfigLoader {
    public static ServiceConfig load(String configPath) {
        try (InputStream inputStream = ServiceConfigLoader.class.getClassLoader()
            .getResourceAsStream(configPath)) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(inputStream, ServiceConfig.class);
        } catch (IOException e) {
            throw new ConfigurationLoadException("Failed to load service configuration", e);
        }
    }
}
