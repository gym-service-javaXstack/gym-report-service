package com.example.gymreport.mongo.repository;

import com.example.gymreport.model.TrainerSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "mongo", matchIfMissing = true)
public interface MongoTrainerWorkLoadRepository extends MongoRepository<TrainerSummary, String> {
}
