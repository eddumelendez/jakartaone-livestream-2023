package org.example.jakartaeelivestream2023;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
class WildflyTest extends BaseTest {

	static Network network = Network.newNetwork();

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource("test.sql"),
					"/docker-entrypoint-initdb.d/init.sql")
			.withNetwork(network)
			.withNetworkAliases("postgres");

	// 1. Use image quay.io/wildfly/wildfly:30.0.0.Final-jdk17
	// 2. Expose port 8080
	// 3. Copy file src/test/resources/wildfly/standalone.xml to /opt/jboss/wildfly/standalone/configuration/standalone.xml
	// 4. Copy file src/test/resources/wildfly/module.xml to /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/module.xml
	// 5. Copy file target/jdbc-drivers/postgresql-42.3.1.jar to /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/postgresql-42.3.1.jar
	// 6. Copy file Transferable.of(war("wildfly")) to /opt/jboss/wildfly/standalone/deployments/jakarta-tc.war

	@Container
	private static final GenericContainer<?> wildfly
			= new GenericContainer<>("quay.io/wildfly/wildfly:30.0.0.Final-jdk17")
			.withExposedPorts(8080)
			.withCopyFileToContainer(
					MountableFile.forClasspathResource("wildfly/standalone.xml"),
					"/opt/jboss/wildfly/standalone/configuration/standalone.xml")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource("wildfly/module.xml"),
					"/opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/module.xml")
			.withCopyFileToContainer(
					MountableFile.forHostPath("target/jdbc-drivers/postgresql-42.3.1.jar"),
					"/opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/postgresql-42.3.1.jar")
			.withCopyToContainer(
					Transferable.of(war("wildfly")),
					"/opt/jboss/wildfly/standalone/deployments/jakarta-tc.war")
			.withNetwork(network)
			.dependsOn(postgres);

	@BeforeAll
	static void beforeAll() {
		RestAssured.baseURI = "http://" + wildfly.getHost() + ":" + wildfly.getMappedPort(8080) + "/jakarta-tc";
	}

	@Test
	void test() {
		when().get("/api/animes")
				.then().statusCode(200)
				.body("size()", equalTo(4))
				.log()
				.body();
	}
}
