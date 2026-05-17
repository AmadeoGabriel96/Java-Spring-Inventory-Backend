package com.MoyanoManchon.FinalProject.service;

import com.MoyanoManchon.FinalProject.exception.AlreadyExistException;
import com.MoyanoManchon.FinalProject.model.InvoiceDetails;
import com.MoyanoManchon.FinalProject.repository.InvoiceDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    public InvoiceDetails create(InvoiceDetails newInvoiceDetails) throws AlreadyExistException {

        Optional<InvoiceDetails> invoiceOp = this.invoiceDetailsRepository.findById(newInvoiceDetails.getId());

        if (invoiceOp.isPresent()){
            log.info("La factura ya existe" + newInvoiceDetails);
            throw new AlreadyExistException("La factura ya existe");
        }else{
            return this.invoiceDetailsRepository.save(newInvoiceDetails);
        }

    }

    public List<InvoiceDetails> findByAll(){

        return this.invoiceDetailsRepository.findAll();

    }

}
