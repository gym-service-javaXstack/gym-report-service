package com.example.gymreport.mongo.repository;

import com.example.gymreport.model.TrainerSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "mongo", matchIfMissing = true)
public interface MongoTrainerWorkLoadRepository extends MongoRepository<TrainerSummary, String> {

    @Query("{'firstName': ?0, 'lastName': ?1, 'username': ?2}")
    Optional<TrainerSummary> findByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username);
}
