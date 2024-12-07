package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.CacheService;
import com.currenjin.wharf.domain.DatabaseService;
import com.currenjin.wharf.domain.MessageQueueService;
import com.currenjin.wharf.domain.UnsupportedServiceException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DockerServiceGeneratorTest {

    private final DockerServiceGenerator generator = new DockerServiceGenerator();

    @Test
    void generateMySQLService() {
        DatabaseService mysql = new DatabaseService("mysql", "8.0");

        DockerService service = generator.generate(mysql);

        assertThat(service.getImage()).isEqualTo("mysql:8.0");
        assertThat(service.getEnvironment())
            .contains("MYSQL_ROOT_PASSWORD=rootpassword")
            .contains("MYSQL_DATABASE=myapp");
        assertThat(service.getPorts()).contains("3306:3306");
    }

    @Test
    void generatePostgreSQLService() {
        DatabaseService postgresql = new DatabaseService("postgresql", "15.0");

        DockerService service = generator.generate(postgresql);

        assertThat(service.getImage()).isEqualTo("postgresql:15.0");
        assertThat(service.getEnvironment())
            .contains("POSTGRES_USER=myapp")
            .contains("POSTGRES_PASSWORD=password")
            .contains("POSTGRES_DB=myapp");
        assertThat(service.getPorts()).contains("5432:5432");
    }

    @Test
    void generateRedisService() {
        CacheService redis = new CacheService("redis", "7.0");

        DockerService service = generator.generate(redis);

        assertThat(service.getImage()).isEqualTo("redis:7.0");
        assertThat(service.getPorts()).contains("6379:6379");
    }

    @Test
    void generateRabbitMQService() {
        MessageQueueService rabbitmq = new MessageQueueService("rabbitmq", "3.12");

        DockerService service = generator.generate(rabbitmq);

        assertThat(service.getImage()).isEqualTo("rabbitmq:3.12-management");
        assertThat(service.getPorts())
            .contains("5672:5672")
            .contains("15672:15672");
    }

    @Test
    void throwExceptionForUnsupportedService() {
        DatabaseService unknown = new DatabaseService("unknown", "1.0");

        assertThatThrownBy(() -> generator.generate(unknown))
            .isInstanceOf(UnsupportedServiceException.class)
            .hasMessageContaining("Unsupported service: unknown");
    }
}
