package org.example.jakartaeelivestream2023;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
public class PayaraMicroTest extends BaseTest {

	static Network network = Network.newNetwork();

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource("test.sql"),
					"/docker-entrypoint-initdb.d/init.sql")
			.withNetwork(network)
			.withNetworkAliases("postgres");

	// 1. Use image payara/micro:6.2023.10-jdk17
	// 2. Expose port 8080
	// 3. Copy file Transferable.of(war("payara")) to /opt/payara/deployments/jakarta-tc.war
	// 4. Copy file target/jdbc-drivers/postgresql-42.3.1.jar to /opt/databases/drivers/postgresql-42.3.1.jar
	// 5. Copy file src/test/resources/payara/domain.xml (0777) to /opt/payara/config/domain.xml
	// 6. Wait for log message ".* Payara Micro .* ready in .*\\s"
	// 7. Start Payara Micro with the following command line arguments:
	//    --addLibs /opt/databases/drivers/postgresql-42.3.1.jar
	//    --deploy /opt/payara/deployments/jakarta-tc.war
	//    --domainconfig /opt/payara/config/domain.xml

	@Container
	static GenericContainer<?> payara = new GenericContainer<>("payara/micro:6.2023.10-jdk17")
			.withExposedPorts(8080)
			.withCopyToContainer(
					Transferable.of(war("payara")), "/opt/payara/deployments/jakarta-tc.war")
			.withCopyFileToContainer(
					MountableFile.forHostPath("target/jdbc-drivers/postgresql-42.3.1.jar"),
					"/opt/databases/drivers/postgresql-42.3.1.jar")
			.withCopyFileToContainer(MountableFile.forClasspathResource("payara/domain.xml", 0777),
					"/opt/payara/config/domain.xml")
			.waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
			.withCommand("--addLibs", "/opt/databases/drivers/postgresql-42.3.1.jar",
					"--deploy", "/opt/payara/deployments/jakarta-tc.war",
					"--domainconfig", "/opt/payara/config/domain.xml")
			.withNetwork(network)
			.dependsOn(postgres);

	@BeforeAll
	static void beforeAll() {
		RestAssured.baseURI = "http://" + payara.getHost() + ":" + payara.getMappedPort(8080) + "/jakarta-tc";
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
