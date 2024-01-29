package com.example.employeemangerapp.repo;

import com.example.employeemangerapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom query to fetch events with comments eagerly
    @Query("SELECT e FROM Event e JOIN FETCH e.comments")
    List<Event> findAllWithComments();
}