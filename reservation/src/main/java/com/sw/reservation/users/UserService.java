package com.sw.reservation.users;

import com.sw.reservation.common.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36";

    String stdUrl = "https://student.donga.ac.kr/Login.aspx";
    String stdInfoUrl = "https://student.donga.ac.kr/Univ/SUD/SSUD0000.aspx?m=1";

    String adminUrl = "https://gw.donga.ac.kr/Login.aspx";
    String adminInfoUrl = "https://student.donga.ac.kr/Univ/SUD/SSUD0000.aspx?m=1"; // 수정 필요

    public User createUser(User user){
        return userRepository.save(user);
    }


    @Autowired
    private static Hashtable<String, String> loginUsers = new Hashtable<String, String>();

    /* 로그인이 되어있는지 확인 */
    public boolean isLogin(String id) {
        boolean isLogin = false;
        Enumeration<String> e = loginUsers.keys();
        String key = "";
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            if (id.equals(loginUsers.get(key)))
                isLogin = true;
        }
        return isLogin;
    }

    /* 로그인 */
    public boolean userLogin(UserDto userDto, HttpSession session) throws IOException {
        boolean isLogin = isLogin(userDto.getId());

        if(!isLogin){ /* 로그인이 안되있으면 */
            ResponseEntity<User> user = postStudent(userDto);
            Optional<User> findUser = userRepository.findByIdAndPassword(user.getBody().getId(), user.getBody().getPassword());

            boolean result = false;

            if(!findUser.isEmpty() && findUser.get().getName() != null) result = true;

            if(result){
                loginUsers.put(session.getId(), userDto.getId());
                session.setAttribute("id", userDto.getId());
                System.out.println("Login");
            }

            return result;
        }

        if(isLogin == true) System.out.println("Login 유지");

        return isLogin;
    }

    /* 로그아웃 */
    public void userLogout(HttpSession session) throws Exception {
        loginUsers.remove(session.getId());
        session.invalidate();
        System.out.println("Logout");
    }

    /* 학생 로그인 */
    public ResponseEntity<User> postStudent(UserDto userDto) throws IOException{
        User user = userDto.toEntity();

        String stdId = userDto.getStudentId().toString();
        String stdPw = userDto.getPassword();
        String stdName;

        // hidden (__VIEWSTATE, __VIEWSTATEGENERATOR, __EVENTVALIDATION) 값 가져오기
        Connection.Response loginPageResponse = Jsoup.connect(stdUrl)
                                                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                                    .header("Content-Type", "text/html; charset=utf-8")
                                                    .method(Connection.Method.GET)
                                                    .execute();

        Map<String, String> loginTryCookie = loginPageResponse.cookies();

        Document loginPageDocument = loginPageResponse.parse();
        String viewstate = loginPageDocument.select("#__VIEWSTATE").val();
        String viewstategenerator = loginPageDocument.select("#__VIEWSTATEGENERATOR").val();
        String eventvalidation = loginPageDocument.select("#__EVENTVALIDATION").val();

        Map<String, String> data = new HashMap<>();
        data.put("txtStudentCd", stdId);
        data.put("txtPasswd", stdPw);
        data.put("__VIEWSTATE", viewstate);
        data.put("__VIEWSTATEGENERATOR", viewstategenerator);
        data.put("__EVENTVALIDATION", eventvalidation);
        data.put("ibtnLogin.x", "645");
        data.put("ibtnLogin.y", "249");

        Connection.Response response = Jsoup.connect(stdUrl)
                                            .userAgent(userAgent)
                                            .header("Referer", stdUrl)
                                            .data(data)
                                            .cookies(loginTryCookie)
                                            .method(Connection.Method.POST)
                                            .execute();

        Map<String, String> loginCookie = response.cookies();

        Document InfoPageDocument = Jsoup.connect(stdInfoUrl)
                                        .userAgent(userAgent)
                                        .cookies(loginCookie)
                                        .get();

        stdName = InfoPageDocument.select("#lblKorNm").text();

        if(!stdName.equals("")) {
            System.out.println("Available user");

            Optional<User> findUser = userRepository.findByIdAndPassword(user.getId(), user.getPassword());

            if(findUser.isEmpty()){ // db에 저장 X
                user.setType("1");
                user.setName(stdName);
                user.setCount("0");
                createUser(user);
            } else {
                if(findUser.get().getCount().equals("1")){ // 1회 사용 시간 초과
                    //System.out.println("1회 사용시간 초과");
                    throw new NotFoundException("1회 사용시간 초과");
                }
            }

        } else {
            System.out.println("Denied user");
        }

        return ResponseEntity.status(200).body(user);
    }
}