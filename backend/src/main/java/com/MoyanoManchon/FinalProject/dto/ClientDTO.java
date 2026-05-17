package com.MoyanoManchon.FinalProject.dto;

public class ClientDTO {
    private Long id;
    private String name;
    private String lastname;
    private String docnumber;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String lastname, String docnumber) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.docnumber = docnumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }
}
