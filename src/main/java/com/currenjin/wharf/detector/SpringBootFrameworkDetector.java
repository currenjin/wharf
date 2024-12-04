package com.currenjin.wharf.detector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.currenjin.wharf.domain.Framework;

public class SpringBootFrameworkDetector implements FrameworkDetector {
	@Override
	public Framework detect(Path path) {
		Path buildGradlePath = path.resolve("build.gradle");

		if (!Files.exists(buildGradlePath)) {
			return Framework.UNKNOWN;
		}

		try {
			String buildGradle = Files.readString(buildGradlePath);
			if (buildGradle.contains("org.springframework.boot")) {
				return Framework.SPRING_BOOT;
			}
			return Framework.UNKNOWN;
		} catch (IOException e) {
			return Framework.UNKNOWN;
		}
	}
}
