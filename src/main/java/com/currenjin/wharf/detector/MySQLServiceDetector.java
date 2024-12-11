package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.DatabaseService;
import com.currenjin.wharf.domain.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MySQLServiceDetector implements ServiceDetector {
    private static final String BUILD_GRADLE = "build.gradle";
    private static final String MYSQL_DEPENDENCY = "mysql:mysql-connector-java";

    @Override
    public Service detect(Path path) {
        Path buildGradlePath = path.resolve(BUILD_GRADLE);

        if (!Files.exists(buildGradlePath)) {
            return null;
        }

        try {
            String buildGradle = Files.readString(buildGradlePath);
            if (buildGradle.contains(MYSQL_DEPENDENCY)) {
                return new DatabaseService("mysql", "8.0");
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
