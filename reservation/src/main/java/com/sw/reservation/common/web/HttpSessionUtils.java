package com.sw.reservation.common.web;

import com.sw.reservation.users.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "id";

    /* 로그인된 유저의 존재 여부 판별 */
    public static boolean isLoginUser(HttpSession session) {

        Object sessionUser = session.getAttribute(USER_SESSION_KEY);

        if ( sessionUser == null ) {
            return false;
        }

        return true;
    }

    /* session 에 저장된 값을 가져오는 메서드 */
    public static Long getUserFromSession(HttpSession session) {

        if ( !isLoginUser(session) ) {
            return null;
        }

        return (Long) session.getAttribute(USER_SESSION_KEY);
    }
}
