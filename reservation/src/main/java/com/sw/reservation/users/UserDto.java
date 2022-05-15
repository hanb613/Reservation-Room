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
    private String count;

    @Builder
    public UserDto(String id, String password, String name, String count, String type){
        this.id = id;
        this.password = password;
        this.name = name;
        this.count = count;
        this.type = type;
    }

    public User toEntity(){
        return User.builder()
                    .id(id)
                    .password(password)
                    .name(name)
                    .count(count)
                    .type(type)
                    .build();
    }
}
