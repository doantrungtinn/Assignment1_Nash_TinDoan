package com.tindoan.tshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "order")
public class order {
    @Id
    private Long id;

    public order() {
    }

    public order(customer customer, product product, Integer size, String payment) {
        this.customer = customer;
        this.product = product;
        this.payment = payment;
        this.size = size;
    }

    @Column(name = "payment")
    private String payment;

    @Column(name = "order_date")
    private LocalDate order_date = LocalDate.now();

    @Column(name = "size")
    private static Integer size;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private customer customer;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private product product;

    public static int size() {
        return 0;
    }


    public LocalDate getOrder_date() {
        return this.order_date;
    }

    public void setOrder_date(LocalDate date) {
        this.order_date = date;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    public product getProduct() {
        return product;
    }

    public void setProduct(product product) {
        this.product = product;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
