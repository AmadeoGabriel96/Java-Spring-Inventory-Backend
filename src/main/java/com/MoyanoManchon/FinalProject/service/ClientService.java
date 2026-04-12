package com.MoyanoManchon.FinalProject.service;

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

    public Client create(Client newClient) throws AlreadyExistException {

        Optional<Client> clientOp = this.clientRepository.findByDocNumber(newClient.getDocNumber());

        if (clientOp.isPresent()){
            log.info("El producto que desea agregar ya existe" + newClient);
            throw new AlreadyExistException("El producto que desea agregar ya existe");
        }else{
            return this.clientRepository.save(newClient);
        }

    }

    public Client update(Client newClient, Long id) throws Exception {

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Client> clientOp = this.clientRepository.findById(newClient.getId());

        if(clientOp.isEmpty()){
            log.info("El producto ya existe enla base de datos" +  newClient);
            throw new NotFoundException("El producto ya existe enla base de datos");
        } else{
            Client clientBd = clientOp.get();
            clientBd.setId(newClient.getId());
            clientBd.setName(newClient.getName());
            clientBd.setLastname(newClient.getLastname());
            clientBd.setDocnumber(newClient.getDocnumber());

            return this.clientRepository.save(newClient);
        }

    }

    public Client findById(Long id) throws Exception{

        if (id <= 0){
            throw new Exception("El id no es valido");
        }

        Optional<Client> clientOp = this.clientRepository.findById(id);

        if (clientOp.isPresent()){
            log.info("El cliente que intenta agregar ya existe en la base de datos" + id);
            throw new NotFoundException("El cliente que intenta agregar ya existe en la base de datos");
        } else{
            return clientOp.get();
        }

    }

    public List<Client> findByAll(){

        return this.clientRepository.findAll();

    }

}
