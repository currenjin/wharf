package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.UnsupportedFrameworkException;
import com.currenjin.wharf.domain.UnsupportedServiceException;

public class DockerServiceGenerator {

    public DockerService generate(Service service) {
        return switch (service.name()) {
            case "mysql" -> generateMySQLService(service.version());
            case "postgresql" -> generatePostgreSQLService(service.version());
            case "redis" -> generateRedisService(service.version());
            case "rabbitmq" -> generateRabbitMQService(service.version());
            default -> throw new UnsupportedServiceException(service.name());
        };
    }

    public DockerService generate(Framework framework) {
        return switch (framework) {
            case SPRING_BOOT -> generateSpringBootService();
            case NODE_JS -> generateNodeJsService();
            default -> throw new UnsupportedFrameworkException(framework);
        };
    }

    private DockerService generateSpringBootService() {
        DockerService springBoot = new DockerService("openjdk:17-jdk-slim");
        springBoot.addPort("8080:8080");
        springBoot.addEnvironment("SPRING_PROFILES_ACTIVE=dev");
        return springBoot;
    }

    private DockerService generateNodeJsService() {
        DockerService nodeJs = new DockerService("node:18-alpine");
        nodeJs.addPort("3000:3000");
        nodeJs.addEnvironment("NODE_ENV=development");
        nodeJs.addVolume("./:/app");
        nodeJs.addVolume("/app/node_modules");
        return nodeJs;
    }

    private DockerService generateMySQLService(String version) {
        DockerService mysql = new DockerService("mysql:" + version);
        mysql.addEnvironment("MYSQL_ROOT_PASSWORD=rootpassword");
        mysql.addEnvironment("MYSQL_DATABASE=myapp");
        mysql.addPort("3306:3306");
        return mysql;
    }

    private DockerService generatePostgreSQLService(String version) {
        DockerService postgresql = new DockerService("postgresql:" + version);
        postgresql.addEnvironment("POSTGRES_USER=myapp");
        postgresql.addEnvironment("POSTGRES_PASSWORD=password");
        postgresql.addEnvironment("POSTGRES_DB=myapp");
        postgresql.addPort("5432:5432");
        return postgresql;
    }

    private DockerService generateRedisService(String version) {
        DockerService redis = new DockerService("redis:" + version);
        redis.addPort("6379:6379");
        return redis;
    }

    private DockerService generateRabbitMQService(String version) {
        DockerService rabbitmq = new DockerService("rabbitmq:" + version + "-management");
        rabbitmq.addPort("5672:5672");
        rabbitmq.addPort("15672:15672");
        return rabbitmq;
    }
}
