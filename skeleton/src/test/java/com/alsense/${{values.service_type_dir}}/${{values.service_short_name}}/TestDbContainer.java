package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}};

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.utility.DockerImageName;

public class TestDbContainer implements QuarkusTestResourceLifecycleManager {

    private static final String DEFAULT_CONTAINER_REGISTRY = "ecsdisnpdueacr01.azurecr.io";
    private static final String CI_CONTAINER_REGISTRY = "#{CONTAINERREGISTRY}#";
    private static final String DEFAULT_DOCKER_TAG = "develop.latest";
    private static final String CI_DOCKER_TAG = "#{TIMESCALE_TESTDB_TAG}#";
    private static final String DEFAULT_DOCKER_DB_NAME = "dsidb";
    private static final String CI_DOCKER_DB_NAME = "#{DB}#";
    private static final DockerImageName POSTGRES_IMAGE;
    private static final GenericContainer<?> postgresDb;
    private static final GenericContainer<?> redis;

    private static String getContainerRegistry() {
        //noinspection ConstantConditions: this is to avoid replacetokens to substitute the same token in the check
        if (CI_CONTAINER_REGISTRY.replace("{", "").equals("#CONTAINERREGISTRY}#")) {
            return DEFAULT_CONTAINER_REGISTRY;
        }
        return CI_CONTAINER_REGISTRY;
    }

    private static String getDockerTag() {
        //noinspection ConstantConditions: this is to avoid replacetokens to substitute the same token in the check
        if (CI_DOCKER_TAG.replace("{", "").equals("#TIMESCALE_TESTDB_TAG}#")) {
            return DEFAULT_DOCKER_TAG;
        }
        return CI_DOCKER_TAG;
    }

    private static String getDockerDbName() {
        //noinspection ConstantConditions: this is to avoid replacetokens to substitute the same token in the check
        if (CI_DOCKER_DB_NAME.replace("{", "").equals("#DB}#")) {
            return DEFAULT_DOCKER_DB_NAME;
        }
        return CI_DOCKER_DB_NAME;
    }

    static {
        POSTGRES_IMAGE = DockerImageName
                .parse(getContainerRegistry() + "/timescaledb:testdb." + getDockerTag());
        postgresDb = new GenericContainer<>(POSTGRES_IMAGE)
                .withImagePullPolicy(PullPolicy.alwaysPull())
                .withExposedPorts(5432)
                .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*\\n", 1))
                .withReuse(true);
        postgresDb.start();

        redis = new GenericContainer<>("redis:3-alpine")
            .withExposedPorts(6379);
        redis.start();
    }

    @Override
    public Map<String, String> start() {
        String postgresUrl = "jdbc:postgresql://" +
            postgresDb.getHost() +
            ":" +
            postgresDb.getMappedPort(5432) +
            "/" +
            getDockerDbName();

        String redisUrl = "redis://" +
            redis.getHost() +
            ":" +
            redis.getMappedPort(6379);

        return Map.of("quarkus.datasource.jdbc.url", postgresUrl,
            "quarkus.datasource.readOnly.jdbc.url", postgresUrl,
            "quarkus.redis.hosts", redisUrl);
    }

    @Override
    public void stop() {
        postgresDb.close();
        redis.close();
    }
}
