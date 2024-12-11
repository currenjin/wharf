package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.MessageQueueService;
import com.currenjin.wharf.domain.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RabbitMQServiceDetector implements ServiceDetector {
    private static final String BUILD_GRADLE = "build.gradle";
    private static final String RABBITMQ_DEPENDENCY = "amqp";

    @Override
    public Service detect(Path path) {
        Path buildGradlePath = path.resolve(BUILD_GRADLE);

        if (!Files.exists(buildGradlePath)) {
            return null;
        }

        try {
            String buildGradle = Files.readString(buildGradlePath);
            if (buildGradle.contains(RABBITMQ_DEPENDENCY)) {
                return new MessageQueueService("rabbitmq", "3.12");
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
