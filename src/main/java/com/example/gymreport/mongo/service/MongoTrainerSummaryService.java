package com.example.gymreport.mongo.service;

import com.example.gymreport.model.TrainerSummary;
import com.example.gymreport.mongo.repository.MongoTrainerWorkLoadRepository;
import com.example.gymreport.service.TrainerSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "mongo", matchIfMissing = true)
@Primary
public class MongoTrainerSummaryService implements TrainerSummaryService {
    private final MongoTrainerWorkLoadRepository mongoTrainerWorkLoadRepository;

    @Transactional(transactionManager = "mongoTransactionManager", readOnly = true)
    public Optional<TrainerSummary> findTrainerSummaryByUsername(String username) {
        log.info("Entry MongoTrainerSummaryService findTrainerSummaryByUsername method");
        return mongoTrainerWorkLoadRepository.findById(username);
    }

    @Transactional(transactionManager = "mongoTransactionManager")
    public void saveTrainerSummary(TrainerSummary trainerSummary) {
        log.info("Entry MongoTrainerSummaryService saveTrainerSummary method");
        mongoTrainerWorkLoadRepository.save(trainerSummary);
        log.info("Exit MongoTrainerSummaryService saveTrainerSummary method");
    }
}
