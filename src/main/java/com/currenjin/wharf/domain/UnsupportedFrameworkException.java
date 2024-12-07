package com.currenjin.wharf.domain;

public class UnsupportedFrameworkException extends RuntimeException {
    public UnsupportedFrameworkException(Framework framework) {
        super("Unsupported framework: " + framework);
    }
}
