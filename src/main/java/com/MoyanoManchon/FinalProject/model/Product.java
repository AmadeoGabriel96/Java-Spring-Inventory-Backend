package com.MoyanoManchon.FinalProject.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String code;
    private int stock;
    private double price;

}
