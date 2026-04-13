package com.MoyanoManchon.FinalProject.service;

import org.springframework.transaction.annotation.Transactional;
import com.MoyanoManchon.FinalProject.exception.AlreadyExistException;
import com.MoyanoManchon.FinalProject.exception.NotFoundException;
import com.MoyanoManchon.FinalProject.model.Client;
import com.MoyanoManchon.FinalProject.model.Invoice;
import com.MoyanoManchon.FinalProject.repository.ClientRepository;
import com.MoyanoManchon.FinalProject.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client create(Client newClient) throws Exception {

        if (newClient.getDocNumber() == null || !newClient.getDocnumber().matches("\\d+")) {
            throw new Exception("El DNI debe contener solo números");
        }
        if (newClient.getName() == null || newClient.getName().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (newClient.getLastname() == null || newClient.getLastname().trim().isEmpty()) {
            throw new Exception("El apellido es obligatorio");
        }
        
        Optional<Client> clientOp = this.clientRepository.findByDocNumber(newClient.getDocnumber());
        
        if (clientOp.isPresent()) {
            throw new Exception("Ya existe un cliente con DNI " + newClient.getDocnumber());
        }

        return this.clientRepository.save(newClient);
        
    }

    @Transactional
    public Client update(Client newClient, Long id) throws Exception {

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Client> clientOp = this.clientRepository.findById(id);

        if(clientOp.isEmpty()){
            log.info("El cliente no existe: " + newClient);
            throw new NotFoundException("El cliente no existe");
        } else{
            Client clientBd = clientOp.get();
            clientBd.setName(newClient.getName());
            clientBd.setLastname(newClient.getLastname());
            clientBd.setDocnumber(newClient.getDocnumber());

            return this.clientRepository.save(clientBd);
        }

    }

    public Client findById(Long id) throws Exception{

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Client> clientOp = this.clientRepository.findById(id);

        if (clientOp.isEmpty()){
            throw new NotFoundException("El cliente no existe");
        } else{
            return clientOp.get();
        }

    }

    public List<Client> findByAll(){

        return this.clientRepository.findAll();

    }

}
