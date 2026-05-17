package com.MoyanoManchon.FinalProject.service;

import com.MoyanoManchon.FinalProject.dto.ClientDTO;
import com.MoyanoManchon.FinalProject.dto.InvoiceDTO;
import com.MoyanoManchon.FinalProject.dto.InvoiceDetailsDTO;
import com.MoyanoManchon.FinalProject.dto.ProductDTO;
import com.MoyanoManchon.FinalProject.exception.NotFoundException;
import com.MoyanoManchon.FinalProject.model.Client;
import com.MoyanoManchon.FinalProject.model.Invoice;
import com.MoyanoManchon.FinalProject.model.InvoiceDetails;
import com.MoyanoManchon.FinalProject.model.Product;
import com.MoyanoManchon.FinalProject.repository.ClientRepository;
import com.MoyanoManchon.FinalProject.repository.InvoiceRepository;
import com.MoyanoManchon.FinalProject.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
    
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired 
    private ClientRepository clientRepository;

    @Autowired 
    private ProductRepository productRepository;
    
    @Transactional
    public Invoice create(Invoice newInvoice) throws Exception {
        if (newInvoice.getClient() == null || newInvoice.getClient().getId() == null) {
            throw new Exception("El cliente es obligatorio");
        }
        Client client = clientRepository.findById(newInvoice.getClient().getId()).orElseThrow(() -> new Exception("El cliente no existe"));
        newInvoice.setClient(client);
        if (newInvoice.getInvoiceDetailsList() == null || newInvoice.getInvoiceDetailsList().isEmpty()) {
            throw new Exception("La factura debe tener al menos un producto");
        }
        double total = 0;
        for (InvoiceDetails detail : newInvoice.getInvoiceDetailsList()) {
            if (detail.getProduct() == null || detail.getProduct().getId() == null) {
                throw new Exception("El producto es obligatorio en cada línea");
            }
            if (detail.getAmount() <= 0) {
                throw new Exception("La cantidad debe ser mayor a 0");
            }
            Product product = productRepository.findById(detail.getProduct().getId()).orElseThrow(() -> new Exception("El producto no existe"));
            if (product.getStock() < detail.getAmount()) {
                throw new Exception("Stock insuficiente para: " + product.getDescription() + ". Disponible: " + product.getStock() + ", solicitado: " + detail.getAmount());
            }
            product.setStock(product.getStock() - detail.getAmount());
            productRepository.save(product);
            detail.setProduct(product);
            detail.setPrice(product.getPrice()); // Precio actual del producto
            detail.setInvoice(newInvoice); // Enlace bidireccional
            total += detail.getAmount() * product.getPrice();
        }
        newInvoice.setTotal(total);
        return invoiceRepository.save(newInvoice);
        
    }

    @Transactional
    public Invoice update(Invoice newInvoice, Long id) throws Exception {

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Invoice> invoiceOp = this.invoiceRepository.findById(id);

        if(invoiceOp.isEmpty()){
            throw new NotFoundException("La factura no existe");
        } else{
            Invoice invoiceBd = invoiceOp.get();
            invoiceBd.setClient(newInvoice.getClient());
            invoiceBd.setTotal(newInvoice.getTotal());
            invoiceBd.setInvoiceDetailsList(newInvoice.getInvoiceDetailsList());

            return this.invoiceRepository.save(invoiceBd);
        }

    }

    public Invoice findById(Long id) throws Exception{

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Invoice> invoiceOp = this.invoiceRepository.findById(id);

        if (invoiceOp.isEmpty()){
            throw new NotFoundException("La factura no existe");
        } else{
            return invoiceOp.get();
        }

    }

    public List<Invoice> findByAll(){

        return this.invoiceRepository.findAll();

    }

    private InvoiceDTO convertToDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setCreatedAt(invoice.getCreatedAt());
        dto.setTotal(invoice.getTotal());

        if (invoice.getClient() != null) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(invoice.getClient().getId());
            clientDTO.setName(invoice.getClient().getName());
            clientDTO.setLastname(invoice.getClient().getLastname());
            clientDTO.setDocnumber(invoice.getClient().getDocnumber());
            dto.setClient(clientDTO);
        }

        if (invoice.getInvoiceDetailsList() != null) {
            List<InvoiceDetailsDTO> detailsDTOs = new ArrayList<>();
            for (InvoiceDetails detail : invoice.getInvoiceDetailsList()) {
                InvoiceDetailsDTO detailDTO = new InvoiceDetailsDTO();
                detailDTO.setId(detail.getId());
                detailDTO.setAmount(detail.getAmount());
                detailDTO.setPrice(detail.getPrice());

                if (detail.getProduct() != null) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(detail.getProduct().getId());
                    productDTO.setDescription(detail.getProduct().getDescription());
                    productDTO.setCode(detail.getProduct().getCode());
                    productDTO.setStock(detail.getProduct().getStock());
                    productDTO.setPrice(detail.getProduct().getPrice());
                    detailDTO.setProduct(productDTO);
                }

                detailsDTOs.add(detailDTO);
            }
            dto.setInvoiceDetailsList(detailsDTOs);
        }

        return dto;
    }

    public InvoiceDTO findByIdDTO(Long id) throws Exception {
        if (id <= 0) {
            throw new Exception("El id no es valido");
        }

        Optional<Invoice> invoiceOp = this.invoiceRepository.findByIdWithDetails(id);

        if (invoiceOp.isEmpty()) {
            throw new NotFoundException("La factura no existe");
        } else {
            return convertToDTO(invoiceOp.get());
        }
    }

    public List<InvoiceDTO> findByAllDTO() {
        List<Invoice> invoices = this.invoiceRepository.findAllWithDetails();
        List<InvoiceDTO> dtos = new ArrayList<>();
        for (Invoice invoice : invoices) {
            dtos.add(convertToDTO(invoice));
        }
        return dtos;
    }

}
