package com.tindoan.tshop.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.ui.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
public class product  {


    public product() {

    }


    public product(Integer id, String name, String description, BigDecimal price, int stock, Integer brand_id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.id = id;
        this.brand_id = brand_id;
    }

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "brand_id")
    private Integer brand_id;

    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //    @JsonIgnore
//    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    private Set<order> orders;


}
