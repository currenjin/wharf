package com.currenjin.wharf.detector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import com.currenjin.wharf.domain.DatabaseService;
import com.currenjin.wharf.domain.Service;

public class PostgreSQLServiceDetector implements ServiceDetector {

	private static final String BUILD_GRADLE = "build.gradle";
	private static final String POSTGRESQL_DEPENDENCY = "org.postgresql:postgresql";

	@Override
	public List<Service> detect(Path path) {
		Path buildGradlePath = path.resolve(BUILD_GRADLE);

		if (!Files.exists(buildGradlePath)) {
			return Collections.emptyList();
		}

		try {
			String buildGradle = Files.readString(buildGradlePath);
			if (buildGradle.contains(POSTGRESQL_DEPENDENCY)) {
				return List.of(new DatabaseService("postgresql", "15.0"));
			}
			return Collections.emptyList();
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}
}
