package com.example.gymreport.cucumber.config;

import com.example.gymreport.mongo.repository.MongoTrainerWorkLoadRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;

public class CucumberSpringConfiguration {
    private static final Network NETWORK = Network.newNetwork();

    protected static final MongoDBContainer MONGO = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017)
            .withNetwork(NETWORK)
            .withNetworkAliases("M1")
            .withCommand("--replSet rs0 --bind_ip localhost,M1");

    protected static final GenericContainer<?> ACTIVE_MQ = new GenericContainer<>("apache/activemq-artemis:latest")
            .withExposedPorts(61616, 8161)
            .withEnv("ARTEMIS_USER", "activemq")
            .withEnv("ARTEMIS_PASSWORD", "activemq");

    static {
        MONGO.start();
        ACTIVE_MQ.start();
    }

    @Autowired
    private MongoTrainerWorkLoadRepository mongoTrainerWorkLoadRepository;

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO::getConnectionString);
        registry.add("spring.data.mongodb.replica-set-name", () -> "rs0");
        registry.add("spring.activemq.broker-url",
                () -> "tcp://localhost:" + ACTIVE_MQ.getMappedPort(61616));
    }

    @PostConstruct
    public void init() {
//        // Creating test data for mongo db
//        TrainerSummary trainerSummaryToSave1 = TrainerSummaryUtil.getTrainerSummary1();
//        TrainerSummaryUtil.addYearlySummary(trainerSummaryToSave1, 2025, Month.of(7), 90);
//        mongoTrainerWorkLoadRepository.save(trainerSummaryToSave1);

    }

    @PreDestroy
    public void shutdown() {
        mongoTrainerWorkLoadRepository.deleteAll();

        MONGO.stop();
        ACTIVE_MQ.stop();
    }
}
