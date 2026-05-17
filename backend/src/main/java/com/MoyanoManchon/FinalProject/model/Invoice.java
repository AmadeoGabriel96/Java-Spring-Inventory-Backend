package com.MoyanoManchon.FinalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "invoice")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private Date createdAt;
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();

}

