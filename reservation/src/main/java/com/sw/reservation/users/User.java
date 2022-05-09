package com.sw.reservation.users;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id
    private String type;
    private String id;
    private String password;
    private String name;

}