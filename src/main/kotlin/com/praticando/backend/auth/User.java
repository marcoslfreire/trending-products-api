package com.praticando.backend.auth;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Users" )
public class User extends PanacheEntity {


    @Column ( nullable = false, unique = true)
    public String username;

    @Column(name = "password_hash", nullable = false)
    public String passwordHash;


    public static User findByUsername(String username){
        return find("username", username).firstResult();
    }
}
