package com.currenjin.wharf.loader;

import java.io.IOException;

public class ConfigurationLoadException extends RuntimeException {
    public ConfigurationLoadException(String message, IOException exception) {
        super(message, exception);
    }
}
