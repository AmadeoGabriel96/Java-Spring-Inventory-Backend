package com.MoyanoManchon.FinalProject.service;

import com.MoyanoManchon.FinalProject.exception.AlreadyExistException;
import com.MoyanoManchon.FinalProject.exception.NotFoundException;
import com.MoyanoManchon.FinalProject.model.Product;
import com.MoyanoManchon.FinalProject.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product create(Product newProduct) throws AlreadyExistException {

        if (newProduct.getCode() == null || newProduct.getCode().trim().isEmpty()) {
            throw new Exception("El código es obligatorio");
        }
        if (newProduct.getStock() < 0) {
            throw new Exception("El stock no puede ser negativo");
        }
        if (newProduct.getPrice() <= 0) {
            throw new Exception("El precio debe ser mayor a 0");
        }
        Optional<Product> productOp = this.productRepository.findByCode(newProduct.getCode());

        if (productOp.isPresent()){
            log.info("El producto que desea agregar ya existe" + newProduct);
            throw new AlreadyExistException("El producto que desea agregar ya existe");
        }else{
            return this.productRepository.save(newProduct);
        }
    }

    @Transactional
    public Product update(Product newProduct, Long id) throws Exception {

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Product> productOp = this.productRepository.findById(id);

        if(productOp.isEmpty()){
            throw new NotFoundException("El producto no existe");
        } else{
            Product productBd = productOp.get();
            productBd.setDescription(newProduct.getDescription());
            productBd.setCode(newProduct.getCode());
            productBd.setStock(newProduct.getStock());
            productBd.setPrice(newProduct.getPrice());
            return this.productRepository.save(productBd);
        }
    }

    public Product findById(Long id) throws Exception {

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Product> productOp = this.productRepository.findById(id);

        if (productOp.isEmpty()){
            throw new NotFoundException("El producto no existe");
        } else{
            return productOp.get();
        }

    }

    public List<Product> findByAll(){

        return this.productRepository.findAll();

    }

}


