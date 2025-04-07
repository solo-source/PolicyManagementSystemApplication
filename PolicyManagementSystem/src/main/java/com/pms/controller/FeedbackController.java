package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pms.exception.InvalidEntityException;
import com.pms.entity.Feedback;
import com.pms.service.FeedbackService;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:8031")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(@Valid @RequestBody Feedback feedback) throws InvalidEntityException {
        Feedback result = feedbackService.submitFeedback(feedback);
        // Pass the complete feedback object for the email notification
        feedbackService.sendFeedbackNotification(result);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/update")
    public ResponseEntity<?> updateFeedback(@Valid @RequestBody Feedback feedback) throws InvalidEntityException {
        Feedback result = feedbackService.updateFeedback(
            feedback.getScheme().getId(),
            feedback.getCustomer().getId(),
            feedback);
        // Pass the complete feedback object for the email notification
        feedbackService.sendFeedbackNotification(result);
        return ResponseEntity.ok(result);
    }
   

    
    @GetMapping("/view")
    public ResponseEntity<List<Feedback>> getFeedbackBySchemeAndCustomer(
            @RequestParam int schemeId, 
            @RequestParam String customerId) throws InvalidEntityException {
        List<Feedback> feedbackList = feedbackService.getFeedbackBySchemeAndCustomer(schemeId, customerId);
        if (feedbackList == null || feedbackList.isEmpty()) {
            feedbackList = Collections.emptyList();
        }
        return ResponseEntity.ok(feedbackList);
    }
    
    @GetMapping("/admin")
    public ResponseEntity<List<Feedback>> getAdminFeedback(@RequestParam int schemeId, 
                                                           @RequestParam(required = false) String status) throws InvalidEntityException {
        List<Feedback> feedbackList;
        if (status == null || status.isEmpty()) {
            feedbackList = feedbackService.getFeedbackByScheme(schemeId);
        } else {
            feedbackList = feedbackService.getFeedbackBySchemeAndStatus(schemeId, status);
        }
        if (feedbackList == null) {
            feedbackList = Collections.emptyList();
        }
        return ResponseEntity.ok(feedbackList);
    }
    
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateFeedbackStatus(@RequestParam Long feedbackId, @RequestParam String status) throws InvalidEntityException {
        Feedback result = feedbackService.updateFeedbackStatus(feedbackId, status);
        return ResponseEntity.ok(result);
    }
}
