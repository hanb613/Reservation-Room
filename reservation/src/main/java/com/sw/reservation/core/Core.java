package com.sw.reservation.core;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Core {

    @Id
    private String id;
}
