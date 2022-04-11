package com.sw.reservation.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class User {

    @Id
    private Integer StudentId;
    private String name;
    private String password;
}
