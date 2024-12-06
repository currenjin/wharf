package com.currenjin.wharf.docker;

import com.currenjin.wharf.domain.Service;
import com.currenjin.wharf.domain.UnsupportedServiceException;

public class DockerServiceGenerator {

	public DockerService generate(Service service) {
		return switch (service.getName()) {
			case "mysql" -> generateMySQLService(service.getVersion());
			case "postgresql" -> generatePostgreSQLService(service.getVersion());
			case "redis" -> generateRedisService(service.getVersion());
			case "rabbitmq" -> generateRabbitMQService(service.getVersion());
			default -> throw new UnsupportedServiceException(service.getName());
		};
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
