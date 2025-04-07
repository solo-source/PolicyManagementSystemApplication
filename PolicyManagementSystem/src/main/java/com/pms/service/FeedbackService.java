package com.pms.service;

import com.pms.exception.InvalidEntityException;
import com.pms.entity.Feedback;
import com.pms.entity.Scheme;
import com.pms.entity.Customer;
import com.pms.repository.FeedbackRepository;
import com.pms.repository.SchemeRepository;
import com.pms.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    public Feedback submitFeedback(Feedback feedback) throws InvalidEntityException {
        Optional<Scheme> schemeOpt = schemeRepository.findById(feedback.getScheme().getId());
        Optional<Customer> customerOpt = customerRepository.findById(feedback.getCustomer().getId());

        if (schemeOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Scheme ID: " + feedback.getScheme().getId());
        }
        if (customerOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Customer ID: " + feedback.getCustomer().getId());
        }

        Scheme scheme = schemeOpt.get();
        Customer customer = customerOpt.get();

        
        feedback.setScheme(scheme);
        feedback.setCustomer(customer);
        feedback.setStatus("pending");
        return feedbackRepository.save(feedback);
    }
    
    public Feedback updateFeedbackStatus(Long feedbackId, String status) throws InvalidEntityException {
        Optional<Feedback> feedbackOpt = feedbackRepository.findById(feedbackId);
        if (feedbackOpt.isEmpty()) {
            throw new InvalidEntityException("Feedback not found for id: " + feedbackId);
        }
        Feedback feedback = feedbackOpt.get();
        feedback.setStatus(status);
        return feedbackRepository.save(feedback);
    }
    
    public List<Feedback> getFeedbackBySchemeAndStatus(int schemeId, String status) throws InvalidEntityException {
        Optional<Scheme> schemeOpt = schemeRepository.findById(schemeId);
        if (schemeOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Scheme ID: " + schemeId);
        }
        Scheme scheme = schemeOpt.get();
        List<Feedback> feedbacks = feedbackRepository.findBySchemeAndStatus(scheme, status);
        if (feedbacks.isEmpty()) {
            throw new InvalidEntityException("No feedback found for Scheme ID: " + schemeId + " with status: " + status);
        }
        return feedbacks;
    }

    public Feedback updateFeedback(int schemeId, String customerId, Feedback feedback) throws InvalidEntityException {
        Optional<Scheme> schemeOpt = schemeRepository.findById(schemeId);
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (schemeOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Scheme ID: " + schemeId);
        }
        if (customerOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Customer ID: " + customerId);
        }
        Scheme scheme = schemeOpt.get();
        Customer customer = customerOpt.get();
        List<Feedback> existingFeedbacks = feedbackRepository.findBySchemeAndCustomer(scheme, customer);
        if (existingFeedbacks.isEmpty()) {
            throw new InvalidEntityException("Feedback not found for Scheme ID: " + schemeId + " and Customer ID: " + customerId);
        }
        Feedback existingFeedback = existingFeedbacks.get(0);
        existingFeedback.setRating(feedback.getRating());
        existingFeedback.setComments(feedback.getComments());
        return feedbackRepository.save(existingFeedback);
    }
    
    public List<Feedback> getFeedbackByScheme(int schemeId) throws InvalidEntityException {
        Optional<Scheme> schemeOpt = schemeRepository.findById(schemeId);
        if (schemeOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Scheme ID: " + schemeId);
        }
        Scheme scheme = schemeOpt.get();
        List<Feedback> feedbacks = feedbackRepository.findByScheme(scheme);
        if (feedbacks.isEmpty()) {
            throw new InvalidEntityException("No feedback found for Scheme ID: " + schemeId);
        }
        return feedbacks;
    }
    
    public List<Feedback> getFeedbackBySchemeAndCustomer(int schemeId, String customerId) throws InvalidEntityException {
        Optional<Scheme> schemeOpt = schemeRepository.findById(schemeId);
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        
        if (schemeOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Scheme ID: " + schemeId);
        }
        if (customerOpt.isEmpty()) {
            throw new InvalidEntityException("Invalid Customer ID: " + customerId);
        }
        
        Scheme scheme = schemeOpt.get();
        Customer customer = customerOpt.get();
        List<Feedback> feedbacks = feedbackRepository.findBySchemeAndCustomer(scheme, customer);
        if (feedbacks.isEmpty()) {
            throw new InvalidEntityException("Feedback not found for Scheme ID: " + schemeId + " and Customer ID: " + customerId);
        }
        return feedbacks;
    }
    
    public void sendFeedbackNotification(Feedback feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        // Use the customer's email from the feedback object
        message.setTo(feedback.getCustomer().getEmail());
        message.setSubject("Feedback Received");
        message.setText("Dear " + feedback.getCustomer().getName() + ",\n\n"
                + "Thank you for your feedback!\n\n"
                + "Your comments: " + feedback.getComments() + "\n\n"
                + "Best Regards,\nPolicy Trust");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

}
