package com.sw.reservation.users;

import com.sw.reservation.common.utils.ApiUtils.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.sw.reservation.common.utils.ApiUtils.fail;
import static com.sw.reservation.common.utils.ApiUtils.success;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /* 학생 로그인 */
    @PostMapping("/login")
    public ApiResult<String> login(@RequestBody UserDto userDto, HttpSession session) throws Exception {

        boolean result = userService.userLogin(userDto, session);

        if (result == true) {
            return success("로그인 성공");
        } else {
            return fail("로그인 실패");
        }
    }

    @PostMapping("/logout")
    public ApiResult<String> logout(HttpSession session) throws Exception {
        userService.userLogout(session);

        return success("로그아웃 성공");
    }
}
