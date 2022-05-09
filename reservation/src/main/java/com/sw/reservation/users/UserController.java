package com.sw.reservation.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String login() throws Exception{
        return "/login";
    }

    /* 관리자 (조교 및 교수) 로그인 */
    @RequestMapping(value="/login/0", method=RequestMethod.POST)
    public ResponseEntity<User> loginAdmin(@RequestBody UserDto userDto) throws Exception {

        return userService.postAdmin(userDto);
    }

    /* 학생 로그인 */
    @RequestMapping(value="/login/1", method=RequestMethod.POST)
    public ResponseEntity<User> loginStudent(@RequestBody UserDto userDto) throws Exception {

        return userService.postStudent(userDto);
    }
}
