package com.currenjin.wharf;

import com.currenjin.wharf.cli.CommandLineRunner;

public class WharfApplication {
	public static void main(String[] args) {
		CommandLineRunner runner = new CommandLineRunner();
		String result = runner.run(args);
		System.out.println(result);
	}
}
