package com.pms.entity;

import jakarta.persistence.*;

@Entity
public class UserSchemes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
    private int userId;

    public UserSchemes(Scheme scheme, int userId) {
        this.scheme = scheme;
        this.userId = userId;
    }

    public UserSchemes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
