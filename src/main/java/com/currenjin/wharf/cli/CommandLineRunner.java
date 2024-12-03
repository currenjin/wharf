package com.currenjin.wharf.cli;

public class CommandLineRunner {
	public String run(String[] args) {
		if (args.length < 1) {
			return """
                  Usage:
                  wharf init [path]     Initialize Docker configuration
                  """;
		}
		return "";
	}
}
