package com.example.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    @Value("#{'(' + target.team.name + ')' + target.username}")
    String getUsername();
}
