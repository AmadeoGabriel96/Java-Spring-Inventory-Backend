package com.MoyanoManchon.FinalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Client")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "invoices"})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "docnumber")
    private String docnumber;

    @OneToMany(mappedBy = "client")
    private List<Invoice> invoices = new ArrayList<>();

}
