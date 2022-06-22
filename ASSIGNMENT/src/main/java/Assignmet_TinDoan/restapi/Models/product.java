package Assignmet_TinDoan.restapi.Models;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "inventory_id")
    private Integer inventory_id;

    @Column(name = "price")
    private String price;

}
