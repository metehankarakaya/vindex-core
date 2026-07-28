package com.metehan.vindexcore.recurring.repository;

import com.metehan.vindexcore.recurring.model.Recurring;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringRepository extends MongoRepository<Recurring, String> {

    @Query("{ 'nextDueDate': { $lte: ?0 }, $or: [ { 'endDate': null }, { 'endDate': { $gte: ?0 } } ] }")
    List<Recurring> findDueRecurrings(LocalDate today);

}
