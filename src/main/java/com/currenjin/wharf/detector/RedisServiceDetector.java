package com.currenjin.wharf.detector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import com.currenjin.wharf.domain.CacheService;
import com.currenjin.wharf.domain.Service;

public class RedisServiceDetector implements ServiceDetector {

	private static final String BUILD_GRADLE = "build.gradle";
	private static final String REDIS_DEPENDENCY = "spring-boot-starter-data-redis";

	@Override
	public List<Service> detect(Path path) {
		Path buildGradlePath = path.resolve(BUILD_GRADLE);

		if (!Files.exists(buildGradlePath)) {
			return Collections.emptyList();
		}

		try {
			String buildGradle = Files.readString(buildGradlePath);
			if (buildGradle.contains(REDIS_DEPENDENCY)) {
				return List.of(new CacheService("redis", "7.0"));
			}
			return Collections.emptyList();
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}
}
