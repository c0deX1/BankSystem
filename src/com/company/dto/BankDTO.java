package com.company.dto;

public class BankDTO {
    private long Id;
    private String Name;
    private String Port;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public BankDTO(long id, String name, String port) {
        Id = id;
        Name = name;
        Port = port;
    }
}
