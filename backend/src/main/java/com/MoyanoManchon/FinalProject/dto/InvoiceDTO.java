package com.MoyanoManchon.FinalProject.dto;

import java.util.Date;
import java.util.List;

public class InvoiceDTO {
    private Long id;
    private Date createdAt;
    private double total;
    private ClientDTO client;
    private List<InvoiceDetailsDTO> invoiceDetailsList;

    public InvoiceDTO() {
    }

    public InvoiceDTO(Long id, Date createdAt, double total, ClientDTO client, List<InvoiceDetailsDTO> invoiceDetailsList) {
        this.id = id;
        this.createdAt = createdAt;
        this.total = total;
        this.client = client;
        this.invoiceDetailsList = invoiceDetailsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public List<InvoiceDetailsDTO> getInvoiceDetailsList() {
        return invoiceDetailsList;
    }

    public void setInvoiceDetailsList(List<InvoiceDetailsDTO> invoiceDetailsList) {
        this.invoiceDetailsList = invoiceDetailsList;
    }
}
