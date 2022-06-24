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
    private Object id;

    public customer() {
    }


    public customer(Integer id, String name, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    @Column(name = "name")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                name.equals(that.name) &&
                email.equals(that.email) &&
                password.equals(that.password) &&
                address.equals(that.address);
        //&&
        //ders.equals(that.orders);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, name, email, password, address);
    }
}
