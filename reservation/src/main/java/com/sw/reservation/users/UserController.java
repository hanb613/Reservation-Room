package com.sw.reservation.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /* 메인 홈 화면*/
    @GetMapping("/")
    public String home() {
        return "/home";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    /* 관리자 (조교 및 교수) 로그인 */
//    @RequestMapping(value="/login/admin", method=RequestMethod.POST)
//    public ResponseEntity<User> loginAdmin(@RequestBody UserDto userDto) throws Exception {
//
//        return userService.postAdmin(userDto);
//    }

    /* 학생 로그인 */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@RequestBody UserDto userDto, HttpSession session) throws Exception {

        boolean result = userService.userLogin(userDto, session);

        if (result == true) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        userService.userLogout(session);

        return "redirect:/login";
    }
}
