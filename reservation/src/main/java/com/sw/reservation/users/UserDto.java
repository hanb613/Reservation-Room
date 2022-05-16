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
    private Integer count;

    @Builder
    public UserDto(Long studentId, String password, String name, Integer count, String type){
        this.studentId = studentId;
        this.type = type;
        this.password = password;
        this.name = name;
        this.count = count;
        this.type = type;
    }

    public User toEntity(){
        return User.builder()
                .studentId(studentId)
                .password(password)
                .name(name)
                .count(count)
                .type(type)
                .build();
    }
}
