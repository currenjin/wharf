package com.currenjin.wharf.docker;

public class Dockerfile {
	private final StringBuilder content;

	public Dockerfile() {
		this.content = new StringBuilder();
	}

	public void addLine(String line) {
		content.append(line).append("\n");
	}

	public String getContent() {
		return content.toString();
	}
}
