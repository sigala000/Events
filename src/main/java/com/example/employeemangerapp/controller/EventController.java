package com.example.employeemangerapp.controller;


import com.example.employeemangerapp.model.Comment;
import com.example.employeemangerapp.model.Event;
import com.example.employeemangerapp.repo.CommentRepository;
import com.example.employeemangerapp.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public EventController(EventRepository eventRepository, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
    }

    // Get all events with comments done
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAllWithComments();
    }

    // Get a single event by ID with comments done
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    // Create a new event done
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // Update an existing event done
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id,@RequestBody Event eventRequest) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        // ... update other fields
        return eventRepository.save(event);
    }

    // Delete an event done
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Get all comments for a specific event done
    @GetMapping("/{eventId}/comments")
    public List<Comment> getCommentsForEvent(@PathVariable Long eventId) {
        return commentRepository.findByEventId(eventId);
    }

    // Create a new comment for an event done
    @PostMapping("/{eventId}/comments")

    public Comment createComment(@PathVariable Long eventId,@RequestBody Comment comment) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        comment.setEvent(event);
        return commentRepository.save(comment);
    }
}
