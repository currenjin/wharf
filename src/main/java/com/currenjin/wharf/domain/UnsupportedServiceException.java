package com.currenjin.wharf.domain;

public class UnsupportedServiceException extends RuntimeException {
	public UnsupportedServiceException(String serviceName) {
		super("Unsupported service: " + serviceName);
	}
}
