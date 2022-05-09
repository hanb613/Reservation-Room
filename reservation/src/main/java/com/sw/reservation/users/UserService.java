package com.sw.reservation.users;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36";

    String stdUrl = "https://student.donga.ac.kr/Login.aspx";
    String stdInfoUrl = "https://student.donga.ac.kr/Univ/SUD/SSUD0000.aspx?m=1";

    String adminUrl = "https://gw.donga.ac.kr/Login.aspx";
    String adminInfoUrl = "https://student.donga.ac.kr/Univ/SUD/SSUD0000.aspx?m=1"; // 수정 필요

    Boolean status = null;
    public void setStatus(boolean a){
        this.status = a;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public String getUserId(User user){ return user.getId(); }

    public ResponseEntity<User> postAdmin(UserDto userDto) throws IOException {
        User user = userDto.toEntity();

        String adminId = userDto.getId();
        String adminPw = userDto.getPassword();

        Boolean fla = false;
        setStatus(false);

        // hidden 값 파싱
        Connection.Response loginPageResponse = Jsoup.connect(adminUrl)
                                                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                                    .header("Content-Type", "text/html; charset=utf-8")
                                                    .header("Accept-Encoding", "gzip, deflate, br")
                                                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,und;q=0.6")
                                                    .header("Cache-Control", "max-age=0")
                                                    .userAgent(userAgent)
                                                    .method(Connection.Method.GET)
                                                    .execute();

        Map<String, String> loginTryCookie = loginPageResponse.cookies();

        Document loginPageDocument = loginPageResponse.parse();
        String viewstate = loginPageDocument.select("#__VIEWSTATE").val();
        String viewstategenerator = loginPageDocument.select("#__VIEWSTATEGENERATOR").val();
        String eventvalidation = loginPageDocument.select("#__EVENTVALIDATION").val();
        String relayState = loginPageDocument.select("input[name=RelayState]").val();
        String id = loginPageDocument.select("#id").val();

        Map<String, String> data = new HashMap<>();
        data.put("uid", adminId);
        data.put("upw", adminPw);
        data.put("__VIEWSTATE", viewstate);
        data.put("__VIEWSTATEGENERATOR", viewstategenerator);
        data.put("__EVENTVALIDATION", eventvalidation);
        data.put("RelayState", relayState);
        data.put("id", id);
        data.put("LoginButton.x", "1174");
        data.put("LoginButton.y", "414");

        Connection.Response response = Jsoup.connect(adminUrl)
                                            .header("Referer", adminUrl)
                                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                            .header("Content-Type", "text/html; charset=utf-8")
                                            .header("Accept-Encoding", "gzip, deflate, br")
                                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,und;q=0.6")
                                            .header("Cache-Control", "max-age=0")
                                            .userAgent(userAgent)
                                            .data(data)
                                            .cookies(loginTryCookie)
                                            .method(Connection.Method.POST)
                                            .execute();

        Map<String, String> loginCookie = response.cookies();

        Document InfoPageDocument = Jsoup.connect(adminInfoUrl) // 수정 필요 ; adminInfoUrl
                                        .userAgent(userAgent)
                                        .cookies(loginCookie)
                                        .get();

        // 수정 필요 ; 관리자 -> 로그인 후 페이지 모름
        String adminName;
        adminName = InfoPageDocument.select("#lblKorNm").text();

        if(!adminName.equals("")) {
            fla = true;
            setStatus(true);

            System.out.println("Available user");

            user.setType("0");
            user.setName("관리자");
            createUser(user);
        } else {
            fla = false;
            setStatus(false);

            System.out.println("Denied user");
        }

        return ResponseEntity.status(200).body(user);
    }


    public ResponseEntity<User> postStudent(UserDto userDto) throws IOException {
        User user = userDto.toEntity();

        String stdId = userDto.getId();
        String stdPw = userDto.getPassword();

        Boolean fla = false;
        setStatus(false);

        // hidden (__VIEWSTATE, __VIEWSTATEGENERATOR, __EVENTVALIDATION) 값 파싱
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
        String stdName;
        stdName = InfoPageDocument.select("#lblKorNm").text();

        if(!stdName.equals("")) {
            fla = true;
            setStatus(true);

            System.out.println("Available user");

            user.setType("1");
            user.setName(stdName);
            createUser(user);
        } else {
            fla = false;
            setStatus(false);

            System.out.println("Denied user");
        }

        return ResponseEntity.status(200).body(user);
    }
}
