package com.example.datajpa.dto;

import org.springframework.beans.factory.annotation.Value;

public class UsernameOnlyDTO {

    private final String username;

    public UsernameOnlyDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
