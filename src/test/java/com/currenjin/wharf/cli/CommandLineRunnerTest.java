package com.currenjin.wharf.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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

	@Test
	void initCommandPrintsMessage(@TempDir Path tempDir) {
		CommandLineRunner runner = new CommandLineRunner();
		String[] args = {"init", tempDir.toString()};

		String output = runner.run(args);

		assertThat(output).contains("Initializing Docker configuration");
	}
}
