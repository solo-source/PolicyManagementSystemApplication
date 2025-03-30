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

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
    
    // Submit new feedback
    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(@Valid @RequestBody Feedback feedback) throws InvalidEntityException{
        Feedback result = feedbackService.submitFeedback(feedback);
        feedbackService.sendFeedbackNotification(feedback.getComments());
        return ResponseEntity.ok(result);
    }
    
    // Update existing feedback
    @PostMapping("/update")
    public ResponseEntity<?> updateFeedback(@Valid @RequestBody Feedback feedback) throws InvalidEntityException{
        Feedback result = feedbackService.updateFeedback(
            feedback.getScheme().getId(),
            feedback.getCustomer().getId(),
            feedback);
        feedbackService.sendFeedbackNotification(feedback.getComments());
        return ResponseEntity.ok(result);
    	//return null;
    }
    


    @GetMapping("/view")
    public ResponseEntity<List<Feedback>> getFeedbackBySchemeAndCustomer(
            @RequestParam int schemeId, 
            @RequestParam String customerId) throws InvalidEntityException{
        List<Feedback> feedbackList = feedbackService.getFeedbackBySchemeAndCustomer(schemeId, customerId);
        if (feedbackList == null || feedbackList.isEmpty()) {
            feedbackList = Collections.emptyList();
        }
        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Feedback>> getAdminFeedback(@RequestParam int schemeId, 
                                                           @RequestParam(required = false) String status) throws InvalidEntityException{
        // Validate status
        if (status != null && !status.isEmpty() && !status.equals("pending") && !status.equals("reviewed")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }

        // Fetch feedback list based on status
        List<Feedback> feedbackList;
        if (status == null || status.isEmpty()) { // Treat empty string as "All"
            feedbackList = feedbackService.getFeedbackByScheme(schemeId);
        } else {
            feedbackList = feedbackService.getFeedbackBySchemeAndStatus(schemeId, status);
        }

        // Ensure feedbackList is not null
        if (feedbackList == null) {
            feedbackList = Collections.emptyList();
        }

        return ResponseEntity.ok(feedbackList);
    }

    // Update feedback status (for admin)
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateFeedbackStatus(@RequestParam Long feedbackId, @RequestParam String status) throws InvalidEntityException{
        Feedback result = feedbackService.updateFeedbackStatus(feedbackId, status);
        return ResponseEntity.ok(result);
    }
}