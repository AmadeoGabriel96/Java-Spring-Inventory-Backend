package com.MoyanoManchon.FinalProject.repository;

import com.MoyanoManchon.FinalProject.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Override
    Optional<Invoice> findById(Long aLong);

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.client LEFT JOIN FETCH i.invoiceDetailsList d LEFT JOIN FETCH d.product")
    List<Invoice> findAllWithDetails();

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.client LEFT JOIN FETCH i.invoiceDetailsList d LEFT JOIN FETCH d.product WHERE i.id = :id")
    Optional<Invoice> findByIdWithDetails(Long id);

}
