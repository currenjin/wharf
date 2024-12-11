package com.currenjin.wharf.analyzer;

import com.currenjin.wharf.detector.*;
import com.currenjin.wharf.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class DefaultProjectAnalyzerIntegrationTest {

    private final List<FrameworkDetector> frameworkDetectorList = List.of(
        new SpringBootFrameworkDetector(),
        new NodeFrameworkDetector());

    private final List<ServiceDetector> serviceDetectorList = List.of(
        new MySQLServiceDetector(),
        new PostgreSQLServiceDetector(),
        new RabbitMQServiceDetector(),
        new RedisServiceDetector());

    private DefaultProjectAnalyzer sut;

    @BeforeEach
    void setUp() {
        sut = new DefaultProjectAnalyzer(frameworkDetectorList, serviceDetectorList);
    }

    @Test
    void analyzeSpringBootFromGradle() {
        Project project = sut.analyze(Path.of("src/test/resources/spring-project"));

        assertThat(project.framework()).isEqualTo(Framework.SPRING_BOOT);
    }

    @Test
    void analyzeMysqlFromGradle() {
        Project project = sut.analyze(Path.of("src/test/resources/spring-project/mysql"));

        Service service = project.requiredServices().get(0);
        assertThat(service).isInstanceOf(DatabaseService.class);
        assertThat(service.name()).contains("mysql");
    }

    @Test
    void analyzePostgresqlFromGradle() {
        Project project = sut.analyze(Path.of("src/test/resources/spring-project/postgresql"));

        Service service = project.requiredServices().get(0);
        assertThat(service).isInstanceOf(DatabaseService.class);
        assertThat(service.name()).contains("postgresql");
    }

    @Test
    void analyzeRabbitMqFromGradle() {
        Project project = sut.analyze(Path.of("src/test/resources/spring-project/rabbitmq"));

        Service service = project.requiredServices().get(0);
        assertThat(service).isInstanceOf(MessageQueueService.class);
        assertThat(service.name()).contains("rabbitmq");
    }

    @Test
    void analyzeRedisFromGradle() {
        Project project = sut.analyze(Path.of("src/test/resources/spring-project/redis"));

        Service service = project.requiredServices().get(0);
        assertThat(service).isInstanceOf(CacheService.class);
        assertThat(service.name()).contains("redis");
    }

    @Test
    void analyzeNodeJs() {
        Project project = sut.analyze(Path.of("src/test/resources/node-project"));

        assertThat(project.framework()).isEqualTo(Framework.NODE_JS);
    }
}
