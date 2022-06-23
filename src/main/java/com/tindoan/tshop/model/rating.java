package com.tindoan.tshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Table(name= "rating")

public class rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rating")
    private String rating;

    @Column(name = "review_id")
    private String review_id;

    @Column(name = "user_id")
    private String user_id;
}
