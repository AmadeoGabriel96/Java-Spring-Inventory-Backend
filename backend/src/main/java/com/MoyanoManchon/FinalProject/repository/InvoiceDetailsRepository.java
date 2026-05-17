package com.MoyanoManchon.FinalProject.repository;

import com.MoyanoManchon.FinalProject.model.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {

    @Override
    Optional<InvoiceDetails> findById(Long aLong);

}
