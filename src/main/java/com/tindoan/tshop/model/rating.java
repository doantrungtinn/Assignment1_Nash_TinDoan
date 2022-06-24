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
    private Integer user_id;
//

    //    //ket noi nhieu 1
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private users user;
//
    public rating(){
        
    }
    
    public rating(Integer id, String rating, String review_id, Integer user_id){
        this.id = id;
        this.rating = rating;
        this.review_id = review_id;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }
}
