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
    private String id;
    private String password;
    private String name;
    private String count;
    private String type;
}