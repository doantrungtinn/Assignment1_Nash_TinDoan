package com.tindoan.tshop.model;


import javax.persistence.*;

@Entity
@Table(name = "brand")

public class brand {

    public brand(){

    }

    public brand(Integer id, String brand_name){
        this.id = id;
        this.brand_name = brand_name;
    }

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "brand_name")
    private String brand_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }


}
