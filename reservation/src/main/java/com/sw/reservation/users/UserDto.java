package com.sw.reservation.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private String type;
    private Long studentId;
    private String password;
    private String name;

    @Builder
    public UserDto(String type, Long studentId, String name, String password){
        this.type = type;
        this.studentId = studentId;
        this.password = password;
        this.name = name;
    }

    public User toEntity(){
        return User.builder()
                .type(type)
                .studentId(studentId)
                .password(password)
                .name(name)
                .build();
    }
}
