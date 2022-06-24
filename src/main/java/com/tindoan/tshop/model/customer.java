package com.tindoan.tshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.ui.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customer")
public class customer {
    @Id
    private Object id;

    public customer() {
    }


    public customer(Integer id, String customer_name, String email, String password, String address) {
        this.id = id;
        this.customer_name = customer_name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    @Column(name = "customer_name")
    private String customer_name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<order> orders;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<order> getOrders() {
        try {
            int size = order.size();
        }
        catch (Exception e) {
            return null;
        }
        return orders;
    }

    public void setOrders(Set<order> orders) {
        for (order o : orders) {
            if (o.getCustomer() == null) o.setCustomer(this);
        }
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        customer that = (customer) o;
        return id == that.id &&
                customer_name.equals(that.customer_name) &&
                email.equals(that.email) &&
                password.equals(that.password) &&
                address.equals(that.address);
        //&&
        //ders.equals(that.orders);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, customer_name, email, password, address);
    }


}
