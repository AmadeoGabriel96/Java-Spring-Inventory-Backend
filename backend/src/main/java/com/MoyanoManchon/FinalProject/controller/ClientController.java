package com.MoyanoManchon.FinalProject.controller;

import com.MoyanoManchon.FinalProject.model.Client;
import com.MoyanoManchon.FinalProject.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/")
    public ResponseEntity<Client> create(@RequestBody Client client) throws Exception {
        return new ResponseEntity<>(this.clientService.create(client), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Client> update(@RequestBody Client client, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.clientService.update(client, id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.clientService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Client>> findByAll(){
        return new ResponseEntity<>(this.clientService.findByAll(), HttpStatus.OK);
    }

}
