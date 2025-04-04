package com.pms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import jakarta.persistence.JoinColumn;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;
    
    @Size(max = 255, message = "Comments cannot exceed 255 characters")
    @NotBlank(message = "Comments is required")
    private String comments;
    private String status = "pending"; // Default to "pending"

    
    // Default constructor
    public Feedback() {}

    // Parameterized constructor
    public Feedback(Scheme scheme, Customer customer, int rating, String comments) {
        this.scheme = scheme;
        this.customer = customer;
        this.rating = rating;
        this.comments = comments;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Scheme getScheme() { return scheme; }
    public void setScheme(Scheme scheme) { this.scheme = scheme; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
 // Getter and Setter for status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
    