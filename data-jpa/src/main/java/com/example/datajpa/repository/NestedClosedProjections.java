package com.example.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();

    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}
