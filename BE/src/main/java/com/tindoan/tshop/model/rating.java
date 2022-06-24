package com.tindoan.tshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rating")
public class rating {

    public rating(){

    }

    public rating(Integer id, String comment, String rating_star, Integer account_id, Integer product_id){
        this.id = id;
        this.comment = comment;
        this.rating_star = rating_star;
        this.account_id = account_id;
        this.product_id = product_id;
    }

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating_star")
    private String rating_star;

    @Column(name = "account_id")
    private Integer account_id;

    @Column(name = "product_id")
    private Integer product_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating_star() {
        return rating_star;
    }

    public void setRating_star(String rating_star) {
        this.rating_star = rating_star;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }
}
