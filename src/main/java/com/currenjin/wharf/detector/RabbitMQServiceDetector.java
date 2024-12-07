package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.MessageQueueService;
import com.currenjin.wharf.domain.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class RabbitMQServiceDetector implements ServiceDetector {

    private static final String BUILD_GRADLE = "build.gradle";
    private static final String RABBITMQ_DEPENDENCY = "spring-boot-starter-amqp";

    @Override
    public List<Service> detect(Path path) {
        Path buildGradlePath = path.resolve(BUILD_GRADLE);

        if (!Files.exists(buildGradlePath)) {
            return Collections.emptyList();
        }

        try {
            String buildGradle = Files.readString(buildGradlePath);
            if (buildGradle.contains(RABBITMQ_DEPENDENCY)) {
                return List.of(new MessageQueueService("rabbitmq", "3.12"));
            }
            return Collections.emptyList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
