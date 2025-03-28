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
//        Optional<Scheme> schemeOpt = schemeRepository.findById(feedback.getScheme().getSchemeId());
//        Optional<Customer> customerOpt = customerRepository.findById(feedback.getCustomer().getCustomerId());
//
//        if (schemeOpt.isEmpty()) {
//            throw new InvalidEntityException("Invalid Scheme ID: " + feedback.getScheme().getSchemeId());
//        }
//        if (customerOpt.isEmpty()) {
//            throw new InvalidEntityException("Invalid Customer ID: " + feedback.getCustomer().getCustomerId());
//        }
//
//        Scheme scheme = schemeOpt.get();
//        Customer customer = customerOpt.get();
//        
//        // Update customerName if provided
//        if (feedback.getCustomer().getCustomerName() != null) {
//            customer.setCustomerName(feedback.getCustomer().getCustomerName());
//            customerRepository.save(customer);
//        }
//
//        feedback.setScheme(scheme);
//        feedback.setCustomer(customer);
//        feedback.setStatus("pending"); // Default status
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

    public Feedback updateFeedback(int schemeId, int customerId, Feedback feedback) throws InvalidEntityException {
//        Optional<Scheme> schemeOpt = schemeRepository.findById(schemeId);
//        Optional<Customer> customerOpt = customerRepository.findById(customerId);
//
//        if (schemeOpt.isEmpty()) {
//            throw new InvalidEntityException("Invalid Scheme ID: " + schemeId);
//        }
//        if (customerOpt.isEmpty()) {
//            throw new InvalidEntityException("Invalid Customer ID: " + customerId);
//        }
//
//        Scheme scheme = schemeOpt.get();
//        Customer customer = customerOpt.get();
//
//        List<Feedback> existingFeedbacks = feedbackRepository.findBySchemeAndCustomer(scheme, customer);
//        if (existingFeedbacks.isEmpty()) {
//            throw new InvalidEntityException("Feedback not found for Scheme ID: " + schemeId + " and Customer ID: " + customerId);
//        }
//
//        Feedback existingFeedback = existingFeedbacks.get(0);
//        existingFeedback.setRating(feedback.getRating());
//        existingFeedback.setComments(feedback.getComments());
 //       return feedbackRepository.save(existingFeedback);
        return null;
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
    
    public List<Feedback> getFeedbackBySchemeAndCustomer(int schemeId, int customerId) throws InvalidEntityException {
//        Optional<Scheme> schemeOpt = schemeRepository.findById(schemeId);
//        Optional<Customer> customerOpt = customerRepository.findById(customerId);
//        
//        if (schemeOpt.isEmpty()) {
//            throw new InvalidEntityException("Invalid Scheme ID: " + schemeId);
//        }
//        if (customerOpt.isEmpty()) {
//            throw new InvalidEntityException("Invalid Customer ID: " + customerId);
//        }
//        
//        Scheme scheme = schemeOpt.get();
//        Customer customer = customerOpt.get();
//        List<Feedback> feedbacks = feedbackRepository.findBySchemeAndCustomer(scheme, customer);
//        if (feedbacks.isEmpty()) {
//            throw new InvalidEntityException("Feedback not found for Scheme ID: " + schemeId + " and Customer ID: " + customerId);
//        }
       // return feedbacks;
    	return null;
    }
    public void sendFeedbackNotification(String feedbackMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("23102081@rmd.ac.in");
        message.setSubject("Feedback Received");
        message.setText("Dear User,\n\nThank you for your feedback!\n\n" + feedbackMessage + "\n\nBest Regards,\nYour Company");
        try{
        mailSender.send(message);
        }catch (MailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
