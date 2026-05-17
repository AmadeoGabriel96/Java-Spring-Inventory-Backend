package com.MoyanoManchon.FinalProject.controller;

import com.MoyanoManchon.FinalProject.dto.InvoiceDTO;
import com.MoyanoManchon.FinalProject.model.Invoice;
import com.MoyanoManchon.FinalProject.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping(path = "/")
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) throws Exception {
        return new ResponseEntity<>(this.invoiceService.create(invoice), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Invoice> update(@RequestBody Invoice invoice, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.invoiceService.update(invoice, id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDTO> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.invoiceService.findByIdDTO(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<InvoiceDTO>> findByAll(){
        return new ResponseEntity<>(this.invoiceService.findByAllDTO(), HttpStatus.OK);
    }

}
