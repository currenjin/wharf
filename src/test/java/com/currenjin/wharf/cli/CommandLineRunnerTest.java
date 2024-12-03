package com.currenjin.wharf.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CommandLineRunnerTest {
	@Test
	void printUsageWhenNoArgs() {
		CommandLineRunner runner = new CommandLineRunner();
		String[] args = {};

		String output = runner.run(args);

		assertThat(output)
			.contains("Usage:")
			.contains("wharf init [path]");
	}
}
