package com.currenjin.wharf.cli;

public class CommandLineRunner {
	public String run(String[] args) {
		if (args.length < 1) {
			return """
                  Usage:
                  wharf init [path]     Initialize Docker configuration
                  """;
		}

		String command = args[0];

		if ("init".equals(command)) {
			return "Initializing Docker configuration";
		}

		return "";
	}
}
