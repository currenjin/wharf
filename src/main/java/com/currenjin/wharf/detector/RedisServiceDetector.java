package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.CacheService;
import com.currenjin.wharf.domain.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RedisServiceDetector implements ServiceDetector {
    private static final String BUILD_GRADLE = "build.gradle";
    private static final String REDIS_DEPENDENCY = "spring-boot-starter-data-redis";

    @Override
    public Service detect(Path path) {
        Path buildGradlePath = path.resolve(BUILD_GRADLE);

        if (!Files.exists(buildGradlePath)) {
            return null;
        }

        try {
            String buildGradle = Files.readString(buildGradlePath);
            if (buildGradle.contains(REDIS_DEPENDENCY)) {
                return new CacheService("redis", "7.0");
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
