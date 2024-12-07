package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.DatabaseService;
import com.currenjin.wharf.domain.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class MySQLServiceDetector implements ServiceDetector {

    private static final String BUILD_GRADLE = "build.gradle";
    private static final String MYSQL_DEPENDENCY = "mysql:mysql-connector-java";

    @Override
    public List<Service> detect(Path path) {
        Path buildGradlePath = path.resolve(BUILD_GRADLE);

        if (!Files.exists(buildGradlePath)) {
            return Collections.emptyList();
        }

        try {
            String buildGradle = Files.readString(buildGradlePath);
            if (buildGradle.contains(MYSQL_DEPENDENCY)) {
                return List.of(new DatabaseService("mysql", "8.0"));
            }
            return Collections.emptyList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
