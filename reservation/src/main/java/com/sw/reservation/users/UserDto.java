package com.sw.reservation.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private String type;
    private String id;
    private String password;
    private String name;

    @Builder
    public UserDto(String type, String id, String name, String password){
        this.type = type;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public User toEntity(){
        return User.builder()
                .type(type)
                .id(id)
                .password(password)
                .name(name)
                .build();
    }
}
