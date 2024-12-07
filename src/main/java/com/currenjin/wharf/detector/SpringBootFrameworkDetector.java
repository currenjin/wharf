package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Framework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpringBootFrameworkDetector implements FrameworkDetector {
    public static final String BUILD_GRADLE = "build.gradle";
    public static final String ORG_SPRINGFRAMEWORK_BOOT = "org.springframework.boot";

    @Override
    public Framework detect(Path path) {
        Path buildGradlePath = path.resolve(BUILD_GRADLE);

        if (!Files.exists(buildGradlePath)) {
            return Framework.UNKNOWN;
        }

        try {
            String buildGradle = Files.readString(buildGradlePath);
            if (buildGradle.contains(ORG_SPRINGFRAMEWORK_BOOT)) {
                return Framework.SPRING_BOOT;
            }
            return Framework.UNKNOWN;
        } catch (IOException e) {
            return Framework.UNKNOWN;
        }
    }
}
